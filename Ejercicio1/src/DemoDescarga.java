import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoDescarga {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int tiempoDescarga = 0;
		// Completa una lista de al menos 10 URLS de descarga
	    String urlsArchivos[] = {"http://sunsite.rediris.es/mirror/ubuntu-releases/16.10/ubuntu-16.10-desktop-amd64.iso",
	    						 "http://sunsite.rediris.es/mirror/FreeBSD/ISO-IMAGES-amd64/8.2/FreeBSD-8.2-RELEASE-amd64-dvd1.iso.xz",
	    		                 "http://sunsite.rediris.es/mirror/archlinux/iso/2016.10.01/archlinux-2016.10.01-dual.iso",
	    		                 "http://sunsite.rediris.es/mirror/Lliurex/releases/14.06_64bits/releases/lliurex-escriptori-amd64_1406_20140826.iso",
	    		                 "http://sunsite.rediris.es/mirror/CentOS/7.2.1511/isos/x86_64/CentOS-7-x86_64-Everything-1511.iso" 
	    		                };
	    
	    // TODO: crear el ExecuteService. El thread pool debe ser fijo (2 threads).
	    ExecutorService thPools = Executors.newFixedThreadPool(2);
	    // TODO: crear el CompletionService
	    CompletionService<String> pool = new ExecutorCompletionService<String>(thPools);
	    // TODO: iterar y añadir los callables parametrizados con urlsArchivos y un tiempo
	    // variable de 10 a 20 segundos.
	    for(int i = 0; i < urlsArchivos.length; i++){
	    	System.out.println("DESCARGANDO >>>>"+urlsArchivos[i]);
	    	tiempoDescarga = (int)Math.round(Math.random()*10+10);
	    	pool.submit(new DescargaCallable(urlsArchivos[i], tiempoDescarga));
	    }
	    System.out.println("--------------------------------------------------------------------------------");
	    // TODO: imprimir los resultados a medida que los recibimos.
	    String descarga = "";
	    for(int j = 0; j < urlsArchivos.length; j++){
	    	try {
				descarga = pool.take().get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
	    	System.out.println(descarga);
	    	// TODO: Imprimir el estado del thread pool después de recibir un archivo (toString)
			System.out.println("ESTADO DEL THREAD\n"+thPools.toString()+"\n-------------------------------------------------------------");
	    }
	    // TODO: parar el thread pool de forma ordenada.
		thPools.shutdown();
		System.out.println("ESTADO DEL THREAD\n"+thPools.toString());
	}

}
