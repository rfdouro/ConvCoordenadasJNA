/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author romulo.douro
 */
public class Teste01 {
 public String path(){
  return this.getClass().getResource("").getFile();
 }
 public static void main(String[] args) {
  System.out.println(""+(new Teste01().path()));
 }
}
