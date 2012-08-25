package de.bdh.kb.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader
{

    public Downloader()
    {
    }

    public synchronized void cancel()
    {
        cancelled = true;
    }

    public static void install(String location, String filename)
    {
        cancelled = false;
        count = total = itemCount = itemTotal = 0;
        System.out.println("[CvR] Downloading Dependencies");
        if(cancelled)
            return;
        try
        {
            System.out.println((new StringBuilder()).append("   + ").append(filename).append(" downloading...").toString());
            download(location, filename);
            System.out.println((new StringBuilder()).append("   - ").append(filename).append(" finished.").toString());
            System.out.println((new StringBuilder()).append("[CvR] Downloading ").append(filename).append("...").toString());
        }
        catch(IOException ex)
        {
            System.out.println((new StringBuilder()).append("[CvR] Error Downloading File: ").append(ex).toString());
        }
        return;
    }

    protected static synchronized void download(String location, String filename)
        throws IOException
    {
        URLConnection connection = (new URL(location)).openConnection();
        connection.setUseCaches(false);
        lastModified = connection.getLastModified();
        //int filesize = connection.getContentLength();
        String destination = (new StringBuilder()).append("lib").append(File.separator).append(filename).toString();
        File parentDirectory = (new File(destination)).getParentFile();
        if(parentDirectory != null)
            parentDirectory.mkdirs();
        InputStream in = connection.getInputStream();
        OutputStream out = new FileOutputStream(destination);
        byte buffer[] = new byte[0x10000];
        //int currentCount = 0;
        do
        {
            if(cancelled)
                break;
            int count = in.read(buffer);
            if(count < 0)
                break;
            out.write(buffer, 0, count);
            //currentCount += count;
        } while(true);
        in.close();
        out.close();
    }

    public long getLastModified()
    {
        return lastModified;
    }

    protected static int count;
    protected static int total;
    protected static int itemCount;
    protected static int itemTotal;
    protected static long lastModified;
    protected static String error;
    protected static boolean cancelled;
}
