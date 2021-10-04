/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.controller;

import com.mine.lazy.LazyDataModelBaseNew;
import com.mine.util.MessageUtil;
import com.mine.util.Util;
import com.mine.model.Game;
import com.mine.persistence.GameServiceImpl;
import com.mine.util.AccentRemover;
import com.mine.util.Config;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cert2
 */
@ManagedBean
@ViewScoped
public class GameController {

    protected final Logger LOGGER = Logger.getLogger(GameController.class.getSimpleName());

    @ManagedProperty("#{gameService}")
    private GameServiceImpl gameService;

    private LazyDataModel<Game> lstGames;
    private List<Game> selectedGame;
    private Game addEditObj = new Game();
    private boolean isEdit;

    public Game getAddEditObj() {
        return addEditObj;
    }

    public void setAddEditObj(Game addEditObj) {
        this.addEditObj = addEditObj;
    }

    public List<Game> getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(List<Game> selectedGame) {
        this.selectedGame = selectedGame;
    }

    public GameServiceImpl getGameService() {
        return gameService;
    }

    public void setGameService(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    public LazyDataModel<Game> getLstGames() {
        return lstGames;
    }

    public void setLstGames(LazyDataModel<Game> lstGames) {
        this.lstGames = lstGames;
    }

    @PostConstruct
    public void onStart() {
        lstGames = new LazyDataModelBaseNew<>(gameService, null, null);
    }

    public void onPrepareUpdateOrAdd(boolean isEdit, Game editGame) {
        if (!isEdit) {
            addEditObj = new Game();
        } else {
            addEditObj = editGame;
        }
    }

    public void handleIconUpload(FileUploadEvent event) {
        addEditObj.setIconImage(event.getFile());
    }

    public void handleImageGameUpload(FileUploadEvent event) {
        addEditObj.getLstImages().add(event.getFile());
    }

    public void handleGameUpload(FileUploadEvent event) {
        addEditObj.setFileGame(event.getFile());
    }

    public void onDelete() {
        try {
            gameService.delete(selectedGame);
            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception ex) {
            LOGGER.error(ex.toString());

        }
    }

    public void removeImage(String removeImageName) {
        // Xoa trong danh sach
        String tmp = addEditObj.getImageThumbs().replace(removeImageName, "");
        if (tmp.length() > 0) {
            tmp = tmp.replace(";;", ";");
            if (tmp.indexOf(";") == 0) {
                tmp = tmp.substring(1, tmp.length() - 1);
            }
            if (tmp.indexOf(";") == tmp.length() - 1) {
                tmp = tmp.substring(0, tmp.length() - 1);
            }
        }
        if (tmp.length() > 0) {
            addEditObj.setImageThumbs(tmp);
        } else {
            addEditObj.setImageThumbs(null);
        }
        try {
            gameService.saveOrUpdate(addEditObj);
            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception ex) {
            LOGGER.error(ex);
            MessageUtil
                    .setErrorMessageFromRes("Lỗi:" + ex);
        }
    }

    public void onSaveOrUpdate() {
        try {
            if (addEditObj.getIconImage() != null) {
                String[] x = addEditObj.getIconImage().getFileName().split("\\.");
                String savedFilename = (new Date()).getTime() + "_" + AccentRemover.removeAccent(addEditObj.getIconImage().getFileName()).replaceAll("  ", " ").replace(" ", "_");
                if (!Util.storeFile(Config.ICON_IMAGE_FOLDER, addEditObj.getIconImage(), savedFilename)) {
                    MessageUtil
                            .setErrorMessageFromRes("Không lưu được icon của game");
                    return;
                }
                addEditObj.setIconPath(Config.ICON_IMAGE_FOLDER + "/" + savedFilename);
            }
            if (addEditObj.getLstImages().size() > 0) {
                String folderName = String.valueOf(addEditObj.getGameId());
                String tmp = null;
                for (int i = 0; i < addEditObj.getLstImages().size(); i++) {
                    String savedFilename = (new Date()).getTime() + "_" + AccentRemover.removeAccent(addEditObj.getLstImages().get(i).getFileName()).replaceAll("  ", " ").replace(" ", "_");
                    if (!Util.storeFile(Config.GAME_IMAGE_FOLDER + "/" + folderName, addEditObj.getLstImages().get(i), savedFilename)) {
                        MessageUtil
                                .setErrorMessageFromRes("Không lưu được file thumbs của game");
                        return;
                    }
                    if (addEditObj.getLstImageThumbs().size() == 0) {
                        tmp = Config.GAME_IMAGE_FOLDER + "/" + folderName + "/" + savedFilename;
                    } else {
                        tmp = addEditObj.getImageThumbs() + ";" + Config.GAME_IMAGE_FOLDER + "/" + folderName + "/" + savedFilename;
                    }
                    addEditObj.setImageThumbs(tmp);
                }
            }
            if (addEditObj.getFileGame() != null) {
                String savedFilename = (new Date()).getTime() + "_" + AccentRemover.removeAccent(addEditObj.getFileGame().getFileName()).replaceAll("  ", " ").replace(" ", "_");
                if (!Util.storeFile(Config.GAME_FILE_FOLDER, addEditObj.getFileGame(), savedFilename)) {
                    MessageUtil
                            .setErrorMessageFromRes("Không lưu được file chạy của game");
                    return;
                }
                addEditObj.setSize((double)addEditObj.getFileGame().getSize()/1024/1024);
                addEditObj.setLinkDownload(Config.GAME_FILE_FOLDER + "/" + savedFilename);

            }
            addEditObj.setFileGame(null);
            addEditObj.setLstImages(new ArrayList<UploadedFile>());
            addEditObj.setIconImage(null);
            if (!isEdit) {
                addEditObj.setCreateDate(new Date());
            }
            gameService.saveOrUpdate(addEditObj);

            MessageUtil.setInfoMessage("Thao tác thành công");
        } catch (Exception ex) {
            LOGGER.info("Thao tác thất bại:", ex);
            MessageUtil.setErrorMessage("Thao tác thất bại:" + ex.getMessage());
        }
    }

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

}
