package com.mine.controller;

import com.mine.util.Config;
import com.mine.util.Util;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ManagedBean
@RequestScoped
public class AudioStreamer {

//    @EJB
//    private ImageService service;

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
        	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			String folderPath = request.getSession().getServletContext().getRealPath("resources");
			String defaultImage = folderPath + File.separator +"olympos-layout"+File.separator+"images"+File.separator+"image-gray.svg";
            String fileName = context.getExternalContext().getRequestParameterMap().get("fileName");
            String file = Util.getUploadFolder(Config.IMAGE_FOLDER)+File.separator+fileName;
			
			try {
				Path path = Paths.get(file);
				byte[] buf = Files.readAllBytes(path);
				return new DefaultStreamedContent(new ByteArrayInputStream(buf));
			} catch (Exception e) {
				//e.printStackTrace();
				try {
					Path path = Paths.get(defaultImage);
					byte[] buf = Files.readAllBytes(path);
					return new DefaultStreamedContent(new ByteArrayInputStream(buf),"image/svg+xml");
				} catch (Exception e1) {
					//e.printStackTrace();
					
				}
			}
            
        }
		return new DefaultStreamedContent();
    }
    public StreamedContent getAudio() {
    	try {
			String defaultImage = "a1.mp3";
			String file = Util.getUploadFolder(Config.AUDIO_FOLDER)+File.separator+defaultImage;
			Path path = Paths.get(file);
			byte[] buf = Files.readAllBytes(path);
			return new DefaultStreamedContent(new ByteArrayInputStream(buf),"audio/mp3");
		} catch (Exception e1) {
			e1.printStackTrace();
			
		}
		return null;
    	
    }
  
    

}