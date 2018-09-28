package CSC406;

import java.io.*;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.lang.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter outf1;
        outf1 = new PrintWriter( new File("ThreadsCooperating.txt"));
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Buffer Buf1 = new Buffer(outf1);
        Add tadd = new Add(Buf1);
        Delete tdelete = new Delete(Buf1);
        executor.execute(tadd);
        executor.execute(tdelete);
        System.out.flush();
        outf1.flush();
        executor.shutdown();
        while(!executor.isTerminated());

    }

}
