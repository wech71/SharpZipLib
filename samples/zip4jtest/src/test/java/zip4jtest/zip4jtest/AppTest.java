package zip4jtest.zip4jtest;

//import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest 
{
	static byte[] testZip = null;
	
    @Test    
    public void test001CreateZip()
    {
    	try
		{
    		testZip = App.createTestZip();
		}
		catch (Exception e)
		{
			org.junit.Assert.fail(e.getMessage());
		}
    }

    @Test
    public void test002SaveZip()
    {
    	try
		{
    		// export zip to file for test with SharpZipLib
    		try(java.io.OutputStream zipOutStm = new java.io.FileOutputStream("zip4j-stored-entries.zip"))
    		{
    			zipOutStm.write(testZip);
    		}
		}
		catch (Exception e)
		{
			org.junit.Assert.fail(e.getMessage());
		}
    }

    
    @Test
    public void test003ReadZip()
    {
    	try
		{
			App.readTestZip(testZip);
		}
		catch (Exception e)
		{
			org.junit.Assert.fail(e.getMessage());
		}
    }
}
