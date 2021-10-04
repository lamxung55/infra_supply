/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.model;

import com.mine.util.Config;
import com.mine.util.Util;
import org.hibernate.annotations.GenericGenerator;
import org.primefaces.model.UploadedFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cert2
 */
@Entity
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GAME_ID")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long gameId;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "LINK_DOWNLOAD")
    private String linkDownload;
    @Column(name = "URL")
    private String url;
    @Column(name = "IS_URL")
    private Short isUrl;
    @Column(name = "ICON_PATH")
    private String iconPath;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "DOWN_TIMES")
    private Long downTimes;
    @Column(name = "VOTE_TIMES")
    private Long voteTimes;
    @Column(name = "STAR_AVG")
    private Double starAvg;
    @Column(name = "SIZE")
    private Double size;
    @Column(name = "IMAGE_THUMBS")
    private String imageThumbs;
    @Column(name = "CURRENT_VERSION")
    private String currentVersion;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "PRODUCT_ID")
    private String productId;
    @Transient
    private UploadedFile fileGame;
    @Transient
    private UploadedFile iconImage;
    @Transient
    private List<UploadedFile> lstImages = new ArrayList<>();
//    @Transient
//    private List<String> lstImageThumbs;
//    @Transient
//    private List<String> lstImageThumbs222;

//    @Transient
//    private String thumbs222;

//    public List<String> getLstImageThumbs222() {
//        return lstImageThumbs222;
//    }
//
//    public void setLstImageThumbs222(List<String> lstImageThumbs222) {
//        this.lstImageThumbs222 = lstImageThumbs222;
//    }

//    public String getThumbs222() {
//        return thumbs222;
//    }
//
//    public void setThumbs222(String thumbs222) {
//        this.thumbs222 = thumbs222;
//    }

    public Game() {
    }

    public Game(Long gameId) {
        this.gameId = gameId;
    }

    public Game(Long gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Short getIsUrl() {
        return isUrl;
    }

    public void setIsUrl(Short isUrl) {
        this.isUrl = isUrl;
    }
    
    

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(Long downTimes) {
        this.downTimes = downTimes;
    }

    public Long getVoteTimes() {
        return voteTimes;
    }

    public void setVoteTimes(Long voteTimes) {
        this.voteTimes = voteTimes;
    }

    public Double getStarAvg() {
        return starAvg;
    }

    public void setStarAvg(Double starAvg) {
        this.starAvg = starAvg;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public UploadedFile getIconImage() {
        return iconImage;
    }

    public void setIconImage(UploadedFile iconImage) {
        this.iconImage = iconImage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<UploadedFile> getLstImages() {
        return lstImages;
    }

    public void setLstImages(List<UploadedFile> lstImages) {
        this.lstImages = lstImages;
    }

    public String getImageThumbs() {
        return imageThumbs;
    }

    public void setImageThumbs(String imageThumbs) {
        this.imageThumbs = imageThumbs;
    }

    public String getIconRealPath() {
        if (iconPath != null && iconPath.length() > 0) {
            return Util.getUploadFolder(Config.ICON_IMAGE_FOLDER) + iconPath;
        }
        return null;
    }

    public List getLstImageThumbs() {
        if (imageThumbs != null && imageThumbs.length() > 0) {
            return new ArrayList<String>(Arrays.asList(imageThumbs.split(";")));
        }
        return new ArrayList<>();
    }

//    public void setLstImageThumbs(List lstImageThumbs) {
//        this.lstImageThumbs = lstImageThumbs;
//    }

    public UploadedFile getFileGame() {
        return fileGame;
    }

    public void setFileGame(UploadedFile fileGame) {
        this.fileGame = fileGame;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mine.model.Game[ gameId=" + gameId + " ]";
    }

}
