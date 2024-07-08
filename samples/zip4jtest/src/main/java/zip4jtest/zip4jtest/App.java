package zip4jtest.zip4jtest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	byte[] zipFileBytes = createTestZip();
		
		// save zip to file for test with SharpZipLib
		try(java.io.OutputStream zipOutStm = new java.io.FileOutputStream("zip4j-stored-entries.zip"))
		{
			zipOutStm.write(zipFileBytes);
		}

    	readTestZip(zipFileBytes);
    }

	static byte[] createTestZip() throws Exception
	{
		// create zip file
		ByteArrayOutputStream zipFileByteStm = new ByteArrayOutputStream();
		ZipOutputStream zipFileOutStm = new ZipOutputStream(zipFileByteStm, "password".toCharArray());

		addZipEntry(zipFileOutStm, "file1", new byte[] { 42 });
		addZipEntry(zipFileOutStm, "file2", "testfile".getBytes(Charset.defaultCharset()) );

		zipFileOutStm.close();

		return zipFileByteStm.toByteArray();		
	}
	
	static void addZipEntry(ZipOutputStream zip, String name, byte[] data) throws IOException
	{
		ZipParameters e = new ZipParameters();
		e.setFileNameInZip(name);
		e.setCompressionMethod(CompressionMethod.STORE);
		e.setEntrySize(data.length);
		java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
		crc32.update(data);
		e.setEntryCRC(crc32.getValue());

		zip.putNextEntry(e);
		zip.write(data, 0, data.length);
		zip.closeEntry();
	}


	static void readTestZip(byte[] zipFileBytes) throws Exception	
	{
		//read with zip4j
		ZipInputStream zipFile = new ZipInputStream(new ByteArrayInputStream(zipFileBytes));
		while(true)
		{
			LocalFileHeader entry = zipFile.getNextEntry();
			if (entry == null)
				break;
			byte[] buf = new byte[(int)entry.getUncompressedSize()];
			int bytesRead = zipFile.read(buf, 0, buf.length);
			System.out.print(entry.getFileName() + " read " + String.valueOf(bytesRead) + " bytes\n");
		}
	}

}
