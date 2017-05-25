
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author romulo.douro
 */
public class Projection {

 private IntByReference prj = null;
 private String out_def = null;
 public static IProj proj = null;
 private String definition = null;
 //JAXBContext jc = JAXBContext.newInstance("com.acme.foo");
 //private Marshaller marshall;
 
 public static String caminhoDLL = "";

 /*static {
  proj = (IProj) Native.loadLibrary("C:\\Program Files\\QGIS Wien\\bin\\proj.dll", IProj.class);
 }*/
 
 public static void carregaDLL(){
  proj = (IProj) Native.loadLibrary(caminhoDLL, IProj.class);
 }

 private void Initialize(String definition) {
  IntByReference thePrj = proj.pj_init_plus(definition);
  if (thePrj == null) {
   String message = GetErrorMessage(GetErrNo());
   throw new RuntimeException(message);
  }
  this.prj = thePrj;
  this.out_def = null;
 }

 public static int GetErrNo() {
  int errno = 0;
  IntByReference pErrNo = proj.pj_get_errno_ref();
  errno = Integer.parseInt("" + pErrNo);
  return errno;
 }

 public static String GetErrorMessage(int errno) {
  if (errno == 0) {
   return null;
  }
  IntByReference pMsg = proj.pj_strerrno(errno);
  //return marshall. Marshal.PtrToStringAnsi(pMsg);
  return "" + pMsg;
 }

 private void CheckInitialized() {
  Projection.CheckInitialized(this);
 }

 private static void CheckInitialized(Projection p) {
  if (p.prj == null) {
   throw new RuntimeException("Projection not initialized");
  }
 }

 public Projection() {
 }

 public Projection(String definition) {
  this.Initialize(definition);
 }

 public void setDefinition(String v) {
  this.Initialize(v);
 }

 public String getDefinition() {
  this.CheckInitialized();
  if (this.out_def == null) {
   IntByReference pDef = proj.pj_get_def(this.prj, 0);
   this.out_def = "" + pDef;//Marshal.PtrToStringAnsi(pDef);
   proj.pj_dalloc(pDef);
  }
  return this.out_def;
 }

 public boolean IsLatLong() {
  this.CheckInitialized();
  return (proj.pj_is_latlong(this.prj) == 0) ? false : true;
 }

 public boolean IsGeoCentric() {
  this.CheckInitialized();
  return (proj.pj_is_geocent(this.prj) == 0) ? false : true;
 }

 public Projection GetLatLong() {
  this.CheckInitialized();
  Projection new_prj = new Projection();
  new_prj.prj = proj.pj_latlong_from_proj(this.prj);
  if (new_prj.prj == null) {
   String message = GetErrorMessage(GetErrNo());
   throw new RuntimeException(message);
  }
  return new_prj;
 }

 @Override
 public String toString() {
  return this.getDefinition();
 }

 public static void SetSearchPath(String[] path) {
  if (path != null && path.length > 0) {
   proj.pj_set_searchpath(path.length, path);
  }
 }

 public void Transform(Projection dst, double[] x, double[] y) {
  this.Transform(dst, x, y, null);
 }

 public void Transform(Projection dst, double[] x, double[] y, double[] z) {
  Projection.Transform(this, dst, x, y, z);
 }

 public static void Transform(Projection src, Projection dst,
         double[] x, double[] y) {
  Projection.Transform(src, dst, x, y, null);
 }

 public static void Transform(Projection src, Projection dst,
         double[] x, double[] y, double[] z) {
  Projection.CheckInitialized(src);
  Projection.CheckInitialized(dst);
  if (x == null) {
   throw new RuntimeException("Argument is required : x");
  }
  if (y == null) {
   throw new RuntimeException("Argument is required : y");
  }
  if (x.length != y.length || (z != null && z.length != x.length)) {
   throw new RuntimeException("Coordinate arrays must have the same length");
  }
  if (src.IsLatLong()) {
   for (int i = 0; i < x.length; i++) {
    x[i] *= proj.DEG_TO_RAD;
    y[i] *= proj.DEG_TO_RAD;
   }
  }
  int result = proj.pj_transform(src.prj, dst.prj, x.length, 1, x, y, z);
  if (result != 0) {
   String message = "Tranformation Error";
   int errno = GetErrNo();
   if (errno != 0) {
    message = Projection.GetErrorMessage(errno);
   }
   throw new RuntimeException(message);
  }
  if (dst.IsLatLong()) {
   for (int i = 0; i < x.length; i++) {
    x[i] *= proj.RAD_TO_DEG;
    y[i] *= proj.RAD_TO_DEG;
   }
  }
 }

 @Override
 protected void finalize() throws Throwable {
  super.finalize();
  if (this.prj != null) {
   proj.pj_free(this.prj);
  }
 }

}
