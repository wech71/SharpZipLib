using ICSharpCode.SharpZipLib.Zip;
using System;
using System.IO;

namespace TestReadZip4JStoredEntriesZip
{
	internal class Program
	{
		static void Main(string[] args)
		{
			ReadZip4jTestZip();
		}

		private static void ReadZip4jTestZip()
		{
			using (ZipInputStream stm = new ZipInputStream(new FileStream(@"..\..\..\..\zip4jtest\zip4j-stored-entries.zip", FileMode.Open)))
			{
				while (true)
				{
					ZipEntry entry = stm.GetNextEntry();
					if (entry == null)
						break;

					byte[] buf = new byte[entry.Size];
					int bytesRead = stm.Read(buf, 0, buf.Length);
					Console.WriteLine($"{entry.Name} read {bytesRead} bytes");
				}
			}

		}
	}
}
