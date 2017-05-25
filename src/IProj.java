
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//dados em http://blog.mwrobel.eu/how-to-call-dll-methods-from-java/#a_downloads
//https://shansvex.wordpress.com/2013/05/23/using-a-dll-in-java-with-jna-java-native-access/
//C:\inetpub\wwwroot\dnpmes3\App_Code\proj_api.cs
/**
 *
 * @author romulo.douro
 */
public interface IProj extends Library {

 // Constants for converting coordinates between radians and degrees
 public final double RAD_TO_DEG = 57.29577951308232;
 public final double DEG_TO_RAD = .0174532925199432958;

 public IntByReference FinderFunction(String path);

 public ProjUV pj_fwd(ProjUV LP, IntByReference projPJ);

 public ProjUV pj_inv(ProjUV XY, IntByReference projPJ);

 public int pj_transform(IntByReference src, IntByReference dst,
         int point_count, int point_offset,
         /*[InAttribute, OutAttribute]*/ double[] x,
         /*[InAttribute, OutAttribute]*/ double[] y,
         /*[InAttribute, OutAttribute]*/ double[] z);

 public int pj_datum_transform(IntByReference src, IntByReference dst,
         int point_count, int point_offset,
         /*[InAttribute, OutAttribute]*/ double[] x,
         /*[InAttribute, OutAttribute]*/ double[] y,
         /*[InAttribute, OutAttribute]*/ double[] z);

 public int pj_geocentric_to_geodetic(double a, double es,
         int point_count, int point_offset,
         /*[InAttribute, OutAttribute]*/ double[] x,
         /*[InAttribute, OutAttribute]*/ double[] y,
         /*[InAttribute, OutAttribute]*/ double[] z);

 public int pj_geodetic_to_geocentric(double a, double es,
         int point_count, int point_offset,
         /*[InAttribute, OutAttribute]*/ double[] x,
         /*[InAttribute, OutAttribute]*/ double[] y,
         /*[InAttribute, OutAttribute]*/ double[] z);

 public int pj_compare_datums(IntByReference srcdefn, IntByReference dstdefn);

 public int pj_apply_gridshift(String nadgrids,
         int inverse, int point_count, int point_offset,
         /*[InAttribute, OutAttribute]*/ double[] x,
         /*[InAttribute, OutAttribute]*/ double[] y,
         /*[InAttribute, OutAttribute]*/ double[] z);

 public void pj_deallocate_grids();

 public int pj_is_latlong(IntByReference projPJ);

 public int pj_is_geocent(IntByReference projPJ);

 public void pj_pr_list(IntByReference projPJ);

 public void pj_free(IntByReference projPJ);

 //public void pj_set_finder(FinderFunction f);

 public IntByReference pj_init(int argc, String[] argv);

 public IntByReference pj_init_plus(String pjstr);

 public IntByReference pj_get_def(IntByReference projPJ, int options);

 public IntByReference pj_latlong_from_proj(IntByReference projPJ);

 public IntByReference pj_malloc(int size);

 public void pj_dalloc(IntByReference memory);

 public IntByReference pj_strerrno(int errno);

 public IntByReference pj_get_errno_ref();

 public IntByReference pj_get_release();

 public void pj_set_searchpath(int count, String[] path);

}
