import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SomeFileTest {

  public static void main(String[] args) throws Exception {
    File file = new File("test.hed"); //hinode engine data

    DataToEncrypt dataToEncrypt = new DataToEncrypt();
    dataToEncrypt.value1 = "Hello, World!";
    dataToEncrypt.value2 = 666;

    byte[] dataOutTmp = dataToEncrypt.toString().getBytes();
    byte[] dataOut = new byte[dataOutTmp.length];

    for (int i = 0; i < dataOut.length; i++) {
      dataOut[i] = dataOutTmp[dataOutTmp.length - i - 1];
    }

    FileOutputStream output = new FileOutputStream(file);
    output.write(dataOut, 0, dataOut.length);
    output.flush();
    output.close();

    byte[] dataInTmp = new byte[(int) file.length()];
    byte[] dataIn = new byte[dataInTmp.length];
    InputStream input = new FileInputStream(file);
    input.read(dataInTmp, 0, dataIn.length);
    input.close();

    for (int i = 0; i < dataIn.length; i++) {
      dataIn[i] = dataInTmp[dataInTmp.length - i - 1];
    }

    String[] dataInString = new String(dataIn).split("%%");

    DataToEncrypt dataToEncrypt1 = new DataToEncrypt();
    dataToEncrypt1.value1 = dataInString[0];
    dataToEncrypt1.value2 = Integer.parseInt(dataInString[1]);

    System.out.println("value1: " + dataToEncrypt1.value1 + "; value2: " + dataToEncrypt1.value2);
  }

  public static class DataToEncrypt {
    public String value1;
    public int value2;

    @Override
    public String toString() {
      return value1 + "%%" + value2;
    }
  }

}
