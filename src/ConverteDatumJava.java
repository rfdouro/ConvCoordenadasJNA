
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

/*
 * Projeto de conversão de coordenadas
 */
/**
 *
 * @author romulo.douro
 */
public class ConverteDatumJava {

 public static String caminhoGridFiles = "C:\\DEV\\ProjetosJAVA\\ConvCoordenadasJNA\\GridFiles\\";

 public static String sad69_LL = "+proj=longlat +ellps=aust_SA +nadgrids=" + caminhoGridFiles + "SAD69_003.gsb +nodefs ";
 public static String sad69_UTM = "+proj=utm +zone=<ZONA> +<SULNORTE> +units=m +ellps=aust_SA +nadgrids=" + caminhoGridFiles + "SAD69_003.gsb +nodefs ";
 public static String sad96_LL = "+proj=longlat +ellps=aust_SA +nadgrids=" + caminhoGridFiles + "SAD96_003.gsb +nodefs ";
 public static String sad96_UTM = "+proj=utm +zone=<ZONA> +<SULNORTE> +units=m +ellps=aust_SA +nadgrids=" + caminhoGridFiles + "SAD96_003.gsb +nodefs ";
 public static String sadDoppler_LL = "+proj=longlat +ellps=aust_SA +towgs84=-67.35,3.88,-38.22,0,0,0,0 +no_defs ";
 public static String sadDoppler_UTM = "+proj=utm +zone=<ZONA> +<SULNORTE> +units=m +ellps=aust_SA +towgs84=-67.35,3.88,-38.22,0,0,0,0 +no_defs ";
 public static String sirgas2000_LL = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs ";
 public static String sirgas2000_UTM = "+proj=utm +zone=<ZONA> +<SULNORTE> +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs ";

 //original wgs
 public static String wgs84_LL = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs ";
 //EPSG:900913 mercato
 public static String google_LL = "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs";

 public static double[] converte(int tipoDe, int tipoPara, double lon_X, double lat_Y, int zona) {
  double[] ret = new double[2];

  String sulnorte = "north";
  if (zona < 0) {
   sulnorte = "south";
   zona *= (-1);
  }

  Projection prjde = new Projection(), prjpara = new Projection();
  String de = "", para = "";

  /**
   * observe o tipo 1 sad 69 LL 2 sad 69 UTM 3 sad 96 LL 4 sad 96 UTM 5 sad
   * doppler LL 6 sad doppler UTM 7 sirgas LL 8 sirgas UTM os demais ainda serão
   * implementados
   *
   *
   */
  switch (tipoDe) {
   case 1:
    de = ConverteDatumJava.sad69_LL;
    break;
   case 2:
    de = ConverteDatumJava.sad69_UTM;
    break;
   case 3:
    de = ConverteDatumJava.sad96_LL;
    break;
   case 4:
    de = ConverteDatumJava.sad96_UTM;
    break;
   case 5:
    de = ConverteDatumJava.sadDoppler_LL;
    break;
   case 6:
    de = ConverteDatumJava.sadDoppler_UTM;
    break;
   case 7:
    de = ConverteDatumJava.sirgas2000_LL;
    break;
   case 8:
    de = ConverteDatumJava.sirgas2000_UTM;
    break;
  }

  switch (tipoPara) {
   case 1:
    para = ConverteDatumJava.sad69_LL;
    break;
   case 2:
    para = ConverteDatumJava.sad69_UTM;
    break;
   case 3:
    para = ConverteDatumJava.sad96_LL;
    break;
   case 4:
    para = ConverteDatumJava.sad96_UTM;
    break;
   case 5:
    para = ConverteDatumJava.sadDoppler_LL;
    break;
   case 6:
    para = ConverteDatumJava.sadDoppler_UTM;
    break;
   case 7:
    para = ConverteDatumJava.sirgas2000_LL;
    break;
   case 8:
    para = ConverteDatumJava.sirgas2000_UTM;
    break;
  }

  double[] xCoords = new double[1];
  xCoords[0] = lon_X;
  double[] yCoords = new double[1];
  yCoords[0] = lat_Y;

  de = de.replace("<ZONA>", "" + zona);
  de = de.replace("<SULNORTE>", "" + sulnorte);

  para = para.replace("<ZONA>", "" + zona);
  para = para.replace("<SULNORTE>", "" + sulnorte);

  //GeoUTM gtm = new GeoUTM();
  prjde = new Projection(de);
  prjpara = new Projection(para);

  Projection.Transform(prjde, prjpara, xCoords, yCoords);

  double dblX = xCoords[0];
  double dblY = yCoords[0];

  ret[0] = dblX;//nova longitude - coordenada X
  ret[1] = dblY;//nova latitude - coordenada Y

  return ret;
 }

 public static void main(String[] args) {
  Projection.caminhoDLL = "C:\\DEV\\ProjetosJAVA\\ConvCoordenadasJNA\\dll\\proj.dll";
  
  if(args.length>0){
      Projection.caminhoDLL = args[0];
      ConverteDatumJava.caminhoGridFiles = args[1];
  }
  
  Projection.carregaDLL();
  //double[] r = ConverteDatumJava.converte(5, 7, -40.5, -20.5, -24);
  double[] r = ConverteDatumJava.converte(7, 8, -40.5, -20.5, -24);
  System.out.println("" + r[0] + " / " + r[1]);

 }
}
