package com.tokioschool.web.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jsusi
 * Esta clase se encarga de conseguir una ruta válida dentro de la máquina donde se ejecuta independiente del S.O. , 
 * para convertirla en ruta estática y poder guardar las imágenes
 */
public class ConstantsConfig {

		
	public static final Path IMAGE_DIR_FILM = toDir();
	public static String SOURCE_DIR;
	
	//Ruta relativa en donde se copiaran las imágenes del proyecto.
	static public Path toDir() {
		final String HOME_DIR = System.getProperty("user.home");
		Path toDir = Paths.get(HOME_DIR + "/filmimageproyect");
		return toDir;
	}
	
	
	//Obtiene la ruta absoluta en tiempo de ejecucion del directorio del proyecto donde se encuentran las imagenes
	static public Path fromDir() {
		
		String workingDir = System.getProperty("user.dir");
		Path fromDir = Paths.get(workingDir + SOURCE_DIR);
		return fromDir;
	}
	
}
