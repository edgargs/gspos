//package com.gs.hacom

import com.gs.opengts.util.Payload

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.Future

import groovy.sql.Sql
import java.util.Random

import groovy.transform.Field

//class SendTrama{
    def connDESA = ["192.168.5.35",1433,"sa","hcm123"]
    def dcsDESA = ["192.168.5.38",30100]

    def connBBDD = connDESA
    @Field def srvDCS
    srvDCS    = dcsDESA

def conMatrix = Sql.newInstance("jdbc:sqlserver://${connBBDD[0]}:${connBBDD[1]};databaseName=MATRIX",connBBDD[2],connBBDD[3],"com.microsoft.sqlserver.jdbc.SQLServerDriver")

    @Field def accountID = 5
    @Field def routeID = 4
        
    @Field def cantHilos = 10
    @Field def tiempo = 5 //segundos
        
    @Field List gps
    @Field List route

    static void main(String[] args) {
        /*
        groovy -cp "bin/;lib/sqljdbc42.jar;src/" src/test/groovy/event/SendTrama.groovy
        */
        
        println args
        
        //1. 
        //tramaCalamp()
        
        //2.1
        loadData()
        
        //2.2
        //multiClient()
    }
    
    static void trama() {
        String b = "4D4347500043551600080D166B2B041017002C0023B304A0BA67EFB7B700000000000000000040C50004020ABCD5FEF7FEC4BEFE127A00000B0000000E0B2D00151805E107CE";
        byte[] bytes = hexStringToByteArray(b);
        
        DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("192.168.5.38");
      byte[] sendData = new byte[70];
      byte[] receiveData = new byte[70];
      //String sentence = st;
      sendData = bytes //sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 30400);
      clientSocket.send(sendPacket);
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
    }
    
    void tramaCalamp(String esn, long latitude, long longitude,timenow) {
        def serverDCS = srvDCS[0]
        def portDCS = srvDCS[1]
        
        DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName(serverDCS);
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      //String sentence = st;
      sendData = buildTrama(esn, latitude, longitude,timenow)
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portDCS);
      clientSocket.send(sendPacket);
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());
      //System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
    }
    
    void tramaCalamp() {
        tramaCalamp("1234567788",-120000000,-760000000)
    }
        
    byte[] buildTrama(String esn, long latitude, long longitude,timenow) {
        Payload payload = new Payload();
        
        //Options Header
        payload.writeZeroFill(1); //options byte       
		payload.writeULong(5, 1); //mobile id length
        payload.writeBytes(hexStringToByteArray(esn)) //mobile id       
        payload.writeULong(1, 1) //mobile id type length
        payload.writeULong(1, 1); //mobile id type
        
        //Message Header
        payload.writeULong(1, 1); //service
        payload.writeULong(10, 1); //type
        payload.writeULong(22, 2); //sequence
        
        //Mini Event Report Message
        payload.writeULong(timenow, 4); //timestamp
        payload.writeULong(latitude, 4); //latitude
        payload.writeLong(longitude, 4); //longitude
        payload.writeULong(90, 2); //heading
        def speed = Math.abs(new Random().nextInt() % 80)
        payload.writeULong(speed, 1); //speed 
        payload.writeULong(8, 1); //satellite
        
        payload.writeULong(1, 1); //comm
        
        payload.writeULong(0, 1); //input
        def eventCode = (speed == 0)?113:70
        payload.writeULong(eventCode, 1); //event code (STATUS_LOCATION      = 0xF020;    // 61472)
                                   //            STATUS_MOTION_STOP         = 0xF113;    // 61715
        payload.writeULong(0, 1); //acumm
        /*logger.debug("Accumulators present: " + l5);
        for (long i = 0; i < l5; ++i) {
            long l6 = payload.readULong(4, 0);
            calAmpEvent.addAccumulator(l6);
        }
        logger.info(calAmpEvent.toString());*/
        
        return payload.getBytes();
    }
    
    static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l/2];
        for (int i = 0; i < l; i += 2) {
            data[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
    
    void multiClient() {
        
        def myClosure = {num -> 
                while(true) {
                    def timenow = epoch()
                    //println "Client DCS [${num}]: time=${timenow} "
                    generateCalamp(num,timenow)
                    def timeDiff = epoch() - timenow
                    println "Client DCS [${num}]: time=${timenow} proc=${timeDiff}"
                    sleep(tiempo*1000)
                }
            }
        println "Cantidad Hilos : ${cantHilos}"
        def threadPool = Executors.newFixedThreadPool(cantHilos)
         try {
          List<Future> futures = (1..cantHilos).collect{num->
            threadPool.submit({->
            myClosure num } as Callable);
          }
          // recommended to use following statement to ensure the execution of all tasks.
          futures.each{it.get()}
        }finally {
          threadPool.shutdown()
        }
    }

void generateCalamp(int num,timenow) {
    
    random = new Random()
    randomInt = random.nextInt(route.size())
    //println randomInt

    //println route[randomInt].routeDetailID 
    double latitude = route.get(randomInt).get('latitude')        
    double longitude = route.getAt(randomInt)['longitude']
    
    tramaCalamp(gps[num-1].esn,(long)(latitude*1.0E7),(long)(longitude*1.0E7),timenow)
}

    void loadData(Sql conMatrix, createD = false) {
        //Carga gps
        gps = conMatrix.rows('''SELECT g.gpsID,g.esn FROM MTXGps AS g
	INNER JOIN MTXVehicle AS v
		ON g.vehicleID = v.vehicleID
WHERE v.accountID = ?
and v.routeID = ?
and g.gpsTypeID = 3''',[accountID,routeID])
        //Carga posiciones
        route = conMatrix.rows('SELECT routeDetailID, latitude, longitude FROM MTXRouteDetail WHERE accountID = ? and routeID = ?',[accountID,routeID])
        //conMatrix.close()
        
        println "Total gps: ${gps.size()}"
        println "Total route: ${route.size()}"
                
        //Datos para despacho
        if (createD) {
            int routeTerminalID = 4
            List person = conMatrix.rows("SELECT personID FROM MTXPerson WHERE personTypeID = 1 and accountID = ? and routeID = ?",[accountID,routeID])

            println "Total person: ${person.size()}"

            1.upto(cantHilos,{
                int num = it-1
                List vehicle = conMatrix.rows("SELECT vehicleID FROM MTXVehicle WHERE gpsID = ?",gps[num].gpsID)
                println "Total vehicle: ${vehicle.size()}"
                String service = ''+gps[num].gpsID

                creaDispatch(conMatrix,person[num].personID,vehicle[0].vehicleID, routeTerminalID,service)
            })
        }
        
        conMatrix.close()
    }

void creaDispatch(Sql sql,int personID,int vehicleID, int routeTerminalID,String service) {
    long timestamp = epoch()
    int dispatchStatusID = 2
    sql.execute( "{call dbo.MTX_sp_DispatchNew(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}" ,
             [0,'',accountID,routeID,personID,0,vehicleID,0,0,routeTerminalID,timestamp,service,dispatchStatusID,0,0,0,0] )
    //sql.close()
}

long epoch() {
    now = Calendar.instance
    //println 'now is a ' + now.class.name
    date = now.time
    epoch1 = date.time
    return epoch1/1000
}

void updateVariables(args) {
    if(args.size()>0){
        cantHilos = Integer.parseInt(args[0])
    }
}
//}

println args

updateVariables(args)

loadData(conMatrix,false)

multiClient()