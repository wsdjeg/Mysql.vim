import java.nio.channels.SelectionKey;  
import java.io.IOException;  

/** 
*该接口定义了通用TCPSelectorServer类与特定协议之间的接口， 
*它把与具体协议相关的处理各种I/O的操作分离了出来， 
*以使不同协议都能方便地使用这个基本的服务模式。 
*/  
public interface TCPProtocol{  
    //accept I/O形式  
    void handleAccept(SelectionKey key) throws IOException;  
    //read I/O形式  
    void handleRead(SelectionKey key) throws IOException;  
    //write I/O形式  
    void handleWrite(SelectionKey key) throws IOException;  
}  
