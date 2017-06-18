/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller4app;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import ucn.ArchivoEntrada;
import ucn.Registro;
import ucn.StdIn;
import ucn.StdOut;


public class App implements IApp {
 
    
    private ListaInscripciones listaInscripciones;
    private ListaMensajes listaMensajes;
    private LinkedList listaAsignaturas;
    private ListaPersonas listaPersonas;
    

    public App() {
        
        listaPersonas = new ListaPersonas();
        listaInscripciones = new ListaInscripciones();
        listaAsignaturas = new LinkedList();
        
    }

    // Método de lectura de los archivos .txt
    @Override        
    public void leerInscripciones() {
             
        try {
            ArchivoEntrada in = new ArchivoEntrada("Inscripciones.txt");
            while(!in.isEndFile()){
                Registro reg = in.getRegistro();
                
                String Alias = reg.getString();
                String CodigoA = reg.getString();
                
                Inscripcion i = new Inscripcion(Alias,CodigoA);
                
                listaInscripciones.ingresar(i);               
                }
     
        } catch (IOException ex) {
            System.out.println("No se pudo leer el archivo");
        }

    }
    
   @Override 
   public void leerAsignaturas() {
       
            try {
            
            FileReader f = new FileReader("Asignaturas.txt");
            BufferedReader  br = new BufferedReader(f);
            
            String linea;
            
            while((linea=br.readLine())!=null){
                String[] campos = linea.split(".");
   
                int Cod = Integer.parseInt(campos[0]);
                String Nombre = campos[1];
                int CantPersona = Integer.parseInt(campos[2]);
                
                Asignatura a = new Asignatura(Cod,Nombre,CantPersona);

                listaAsignaturas.add(a);     
                
            }
            
        } catch (IOException ex) {
            System.out.println("No se pudo leer el archivo");
        }
   } 
   
   
   @Override 
   public void leerPersonas() {
       
        try {
            
            FileReader f = new FileReader("creditos.txt");
            BufferedReader  br = new BufferedReader(f);
            
            String linea;
            
            while((linea=br.readLine())!=null){
                String[] campos = linea.split(";");
   
                String Rut = campos[0];
                String Nombre = campos[1];
                String Apellido = campos[2];
                String Correo = campos[3];
                String Estudio = campos[4];
                int Dato1 = Integer.parseInt(campos[5]);
                int Dato2 = Integer.parseInt(campos[6]);   
                
                int n = Correo.indexOf("@");
                String Alias =  Correo.substring(0,n);
                
                if(Estudio.equals("alumno")){
                    listaPersonas.insertarOrdenado(Rut,Nombre,Apellido,Correo,Alias,Dato1,Dato2);
                
                }
                if(Estudio.equals("profesor")){
                    listaPersonas.insertarOrdenado(Rut,Nombre,Apellido,Correo,Alias,Dato1,Dato2);
                
                }
                           
            }

            
        } catch (IOException ex) {
            System.out.println("No se pudo leer el archivo");
        }       
   
   }    
   
   
   // Errores de Registro 
   @Override 
   public void RF1() {
       
       // verificar tipo errores(rut) y mostrarlos
   
   }  
   
   // Despliegue datos de una asignatura
   @Override 
   public void RF2() {
       
       StdOut.println("Ingrese código de la asignatura para mostrar sus datos: ");
       StdOut.println("---------------------");
       int cod = StdIn.readInt();
       
       NodoInscripcion actual = listaInscripciones.getFirst();
       while(actual != null){ // poner algo como getnext != first
           actual.getNext();
           
           if(actual.getInscripcion().getCodigoAsig().equals(cod)){
               
               String alias = actual.getInscripcion().getAlias();
               NodoPersona personaAsig = listaPersonas.getFirst();
               
               while(personaAsig != null){
                   personaAsig.getNext();
                   
                    if(personaAsig.persona.getAlias().equals(alias)){
                      
                       StdOut.println(" Nombre: " + personaAsig.persona.getNombre());
                       StdOut.println(" Apellido: " + personaAsig.persona.getApellido());
                       StdOut.println(" Rut: " + personaAsig.persona.getRut());
                       StdOut.println(" Correo: " + personaAsig.persona.getCorreo());
                       StdOut.println(" Alias: " + personaAsig.persona.getAlias());
                       StdOut.println(" Nota: ");

                      
                       if(personaAsig.persona instanceof Alumno){
                           Alumno alum = (Alumno)personaAsig.persona;
                           
                          StdOut.println(" Cantidad Mensajes enviados: " +alum.getCantMsjEnviadosProfe());
                          StdOut.println(" Cantidad Asignaturas: " + alum.getCantAsignaturas());                   
                       }
                      
                       else{
                           Profesor profe = (Profesor)personaAsig.persona;
                          
                          StdOut.println(" Cantidad Mensajes enviados: " + profe.getCantMsjEnviados());
                          StdOut.println(" Cantidad Mensajes recibidos: " + profe.getCantMsjRecibidos()); 
                     }
                  
                    }
                    StdOut.println("----------------------");

               }
           }       
       }
   } 
   
   // Enviar un mensaje
   @Override 
   public void RF3() {
       
      StdOut.println("-----ENVIAR UN MENSAJE----");
      StdOut.println("Ingrese un alias valido: ");
      String alias = StdIn.readString();
   
      StdOut.println("Ingrese el código de la asignatura del destinatario: ");
      String CodDestino = StdIn.readString();
            
      Iterator ite = listaAsignaturas.iterator();
      
      StdOut.println("Seleccione Destinatario:");
      while(ite.hasNext()){
          Asignatura a = (Asignatura) ite.next();
          
      
         a.getListaPersonas().buscar(codigpersona);
      }
   
   }    
   
   //Registrar una nueva persona en una asignatura.
   @Override 
   public void RF4() {
       // ingresar a la lista de personas y tambien al .txt de inscripcion y persona
   
   } 
   
   // Elimina una persona de una asignatura
   @Override 
   public void RF5() {
       // pedir alias y eliminar la inscripcion
   
   } 
   
   // Accede al registro de mensajes de una persona
   @Override 
   public void RF6() {
       // buscar mensajes segun emisor y receptor
   
   }    
    
}
 
    



    
    
    

