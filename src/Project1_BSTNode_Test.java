
/* Project1_BSTNode_Test
 *
 * CSc 345 Spring 16 - Project 1
 *
 * Author: Russ Lewis
 * TA:     n/a
 *
 * This class provides the main() method, and the testing code, for the
 * BSTNode class.
 *
 * Really, I ought to learn about JUnit - but I haven't done that yet.
 */

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;


public class Project1_BSTNode_Test
{
	public static void main(String[] args)
		throws IOException
	{
		// look for a leaning "--debug" argument - which must come
		// *BEFORE* THe ordinary argument.
		if (args.length > 0 && args[0].equals("--debug"))
		{
			debug_mode = true;
			args = Arrays.copyOfRange(args, 1,args.length);
		}


		if (args.length == 1 && args[0].equals("unittest"))
			generalUnitTest();

		else if (args.length >= 1 && args[0].equals("random"))
		{
			if (args.length > 2)
			{
				System.err.println("ERROR: The 'random' argument takes at most one argument!");
				System.exit(1);
			}

			int seed;
			if (args.length == 1)
				seed = (int)(Math.random() * 1000000000);
			else
				seed = Integer.parseInt(args[1]);

			randomUnitTests(seed);
		}

		else if (args.length == 1 && args[0].equals("sort"))
			sortInput();

		else
		{
			System.err.println("ERROR: Unrecognized command.");
			System.err.println("  Possible command line arguments:");
			System.err.println("    unittest      - Does some basic unit testing of the BSTNode class.");
			System.err.println("    random [seed] - Generates some random strings, and tests a BST using those.  Providing a seed (integer) is optional");
			System.err.println("    sort          - Reads from the keyboard (or a pipe), and writes the sorted output to the screen");
			System.err.println("NOTE: Any argument can have the --debug argument added *BEFORE* it to enter DEBUG mode (which produces lots of .dot files).");
			System.exit(1);
		}
	}

	public static void generalUnitTest()
		throws IOException
	{
		/* step 1: simply check for the existence of various methods */
		BSTNode root = new BSTNode("foo");

		insertHelper(root, "bar");
		insertHelper(root, "wilma");

		String    key     = root.getKey();
		BSTNode   left    = root.getLeft();
		BSTNode   right   = root.getRight();
		int       height  = root.getHeight();
		int       count   = root.getCount();
		String[]  inOrder = root.toArray();
		String[] preOrder = root.toArray_preOrder();
		String    dotFile = root.toDotFile(true);

		//writeDotFile(root, "proj1_unittest_test1.dot");


		/* step 2: Now, assert as much as you can about the BST */
		myAssert(key.equals("foo"));

		myAssert(left != null);
		myAssert(left.getKey().equals("bar"));

		myAssert(right != null);
		myAssert(right.getKey().equals("wilma"));

		myAssert(height == 1);
		myAssert(count == 3);

		myAssert(inOrder.length == 3);
		myAssert(inOrder[0].equals("bar"));
		myAssert(inOrder[1].equals("foo"));
		myAssert(inOrder[2].equals("wilma"));

		myAssert(preOrder.length == 3);
		myAssert(preOrder[0].equals("foo"));
		myAssert(preOrder[1].equals("bar"));
		myAssert(preOrder[2].equals("wilma"));

		myAssert(dotFile != null);
		myAssert(dotFile.length() > 10);


		/* step 3: confirm that the exception is thrown as required */
		try {
			insertHelper(root, "foo");
			myAssert(false);
		} catch (IllegalArgumentException e)
		{
			myAssert(true);
		}

		try {
			insertHelper(root, "bar");
			myAssert(false);
		} catch (IllegalArgumentException e)
		{
			myAssert(true);
		}

		try {
			insertHelper(root, "wilma");
			myAssert(false);
		} catch (IllegalArgumentException e)
		{
			myAssert(true);
		}


		/* step 4: More unit tests, with different input datasets. */
		BSTNode rootAsc = new BSTNode("a");
		insertHelper(rootAsc, "b");
		insertHelper(rootAsc, "c");
		insertHelper(rootAsc, "d");
		insertHelper(rootAsc, "e");
		insertHelper(rootAsc, "f");
		insertHelper(rootAsc, "g");
		insertHelper(rootAsc, "h");
		insertHelper(rootAsc, "i");
		insertHelper(rootAsc, "j");
		writeDotFile(rootAsc, "proj1_unittest_ascending.dot");


		BSTNode rootDes = new BSTNode("j");
		insertHelper(rootDes, "i");
		insertHelper(rootDes, "h");
		insertHelper(rootDes, "g");
		insertHelper(rootDes, "f");
		insertHelper(rootDes, "e");
		insertHelper(rootDes, "d");
		insertHelper(rootDes, "c");
		insertHelper(rootDes, "b");
		insertHelper(rootDes, "a");
		writeDotFile(rootDes, "proj1_unittest_descending.dot");

		BSTNode rootBal = new BSTNode("h");
		insertHelper(rootBal, "d");
		insertHelper(rootBal, "b");
		insertHelper(rootBal, "a");
		insertHelper(rootBal, "c");
		insertHelper(rootBal, "f");
		insertHelper(rootBal, "e");
		insertHelper(rootBal, "g");
		insertHelper(rootBal, "l");
		insertHelper(rootBal, "j");
		insertHelper(rootBal, "i");
		insertHelper(rootBal, "k");
		insertHelper(rootBal, "n");
		insertHelper(rootBal, "m");
		insertHelper(rootBal, "o");
		writeDotFile(rootBal, "proj1_unittest_balanced.dot");

		String[] asc_inOrder = rootAsc.toArray();
		String[] des_inOrder = rootDes.toArray();
		String[] bal_inOrder = rootBal.toArray();
		String[] bal_preOrder = rootBal.toArray_preOrder();

		myAssert(asc_inOrder.length == 10);
		myAssert(asc_inOrder[0].equals("a"));
		myAssert(asc_inOrder[1].equals("b"));
		myAssert(asc_inOrder[2].equals("c"));
		myAssert(asc_inOrder[3].equals("d"));
		myAssert(asc_inOrder[4].equals("e"));
		myAssert(asc_inOrder[5].equals("f"));
		myAssert(asc_inOrder[6].equals("g"));
		myAssert(asc_inOrder[7].equals("h"));
		myAssert(asc_inOrder[8].equals("i"));
		myAssert(asc_inOrder[9].equals("j"));

		myAssert(des_inOrder.length == 10);
		myAssert(des_inOrder[0].equals("a"));
		myAssert(des_inOrder[1].equals("b"));
		myAssert(des_inOrder[2].equals("c"));
		myAssert(des_inOrder[3].equals("d"));
		myAssert(des_inOrder[4].equals("e"));
		myAssert(des_inOrder[5].equals("f"));
		myAssert(des_inOrder[6].equals("g"));
		myAssert(des_inOrder[7].equals("h"));
		myAssert(des_inOrder[8].equals("i"));
		myAssert(des_inOrder[9].equals("j"));

		myAssert(bal_inOrder.length == 15);
		myAssert(bal_inOrder[ 0].equals("a"));
		myAssert(bal_inOrder[ 1].equals("b"));
		myAssert(bal_inOrder[ 2].equals("c"));
		myAssert(bal_inOrder[ 3].equals("d"));
		myAssert(bal_inOrder[ 4].equals("e"));
		myAssert(bal_inOrder[ 5].equals("f"));
		myAssert(bal_inOrder[ 6].equals("g"));
		myAssert(bal_inOrder[ 7].equals("h"));
		myAssert(bal_inOrder[ 8].equals("i"));
		myAssert(bal_inOrder[ 9].equals("j"));
		myAssert(bal_inOrder[10].equals("k"));
		myAssert(bal_inOrder[11].equals("l"));
		myAssert(bal_inOrder[12].equals("m"));
		myAssert(bal_inOrder[13].equals("n"));
		myAssert(bal_inOrder[14].equals("o"));

		myAssert(bal_preOrder.length == 15);
		myAssert(bal_preOrder[ 0].equals("h"));
		myAssert(bal_preOrder[ 1].equals("d"));
		myAssert(bal_preOrder[ 2].equals("b"));
		myAssert(bal_preOrder[ 3].equals("a"));
		myAssert(bal_preOrder[ 4].equals("c"));
		myAssert(bal_preOrder[ 5].equals("f"));
		myAssert(bal_preOrder[ 6].equals("e"));
		myAssert(bal_preOrder[ 7].equals("g"));
		myAssert(bal_preOrder[ 8].equals("l"));
		myAssert(bal_preOrder[ 9].equals("j"));
		myAssert(bal_preOrder[10].equals("i"));
		myAssert(bal_preOrder[11].equals("k"));
		myAssert(bal_preOrder[12].equals("n"));
		myAssert(bal_preOrder[13].equals("m"));
		myAssert(bal_preOrder[14].equals("o"));


		System.out.println("BASIC UNIT TEST *** PASS ***");
	}



	public static void randomUnitTests(int seed)
		throws IOException
	{
		System.out.println("RANDOM UNIT TEST: seed="+seed);
		Random rand = new Random(seed);


		// create a set of strings to insert first.
		int count = 4+rand.nextInt(28);
		String[] orig = new String[count];
		for (int i=0; i<count; i++)
		{
			orig[i] = randomString(rand);
			System.out.println("  data: "+orig[i]);
		}


		// now, build a first tree based on that list.  We insert in
		// current array order, so the first element in the list
		// becomes the root.  At first, I thought that the order of
		// the 'orig' array would match the pre-order traversal of the
		// tree, but that doesn't work because the dataset is
		// partitioned by the first value - and likewise, each subset
		// is sub-partitioned by the first element in the partition.
		BSTNode root1 = new BSTNode(orig[0]);
		for (int i=1; i<count; i++)
			insertHelper(root1, orig[i]);
		writeDotFile(root1, "proj1_random_1.dot");

		myAssert(root1.getCount() == count);

		String[]  inOrder1 = root1.toArray();
		String[] preOrder1 = root1.toArray_preOrder();
		  myAssert( inOrder1.length == count);
		  myAssert(preOrder1.length == count);

		// confirm that the 'inOrder' array is actually in order!
		for (int i=0; i+1<inOrder1.length; i++)
		{
			System.out.println("  inOrder: "+inOrder1[i]);
			myAssert(inOrder1[i].compareTo(inOrder1[i+1]) < 0);
		}
		System.out.println("  inOrder: "+inOrder1[count-1]);


		// if we take the preOrder array and insert into a new array
		// in exactly that order, then the result should be a new
		// BST with *EXACTLY* the same shape.
		BSTNode root2 = new BSTNode(preOrder1[0]);
		for (int i=1; i<count; i++)
			insertHelper(root2, preOrder1[i]);
		writeDotFile(root2, "proj1_random_2.dot");

		String[]  inOrder2 = root2.toArray();
		String[] preOrder2 = root2.toArray_preOrder();
		  myAssert( inOrder2.length == inOrder1.length);
		  myAssert(preOrder2.length == inOrder1.length);

		for (int i=0; i<inOrder2.length; i++)
		{
			myAssert( inOrder2[i].equals( inOrder1[i]));
			myAssert(preOrder2[i].equals(preOrder1[i]));
		}


		// inserting the inOrder array in order should produce a
		// right-leaning linked list (which has the property that the
		// inOrder and preOrder are identical).  Inserting the inOrder
		// array in reverse order should do the exact opposite.
		BSTNode root3 = new BSTNode(inOrder2[0]);
		BSTNode root4 = new BSTNode(inOrder2[count-1]);
		for (int i=1; i<count; i++)
		{
			insertHelper(root3, inOrder2[i]);
			insertHelper(root4, inOrder2[count-1-i]);
		}
		writeDotFile(root3, "proj1_random_3.dot");
		writeDotFile(root4, "proj1_random_4.dot");

		String[]  inOrder3 = root3.toArray();
		String[] preOrder3 = root3.toArray_preOrder();
		String[]  inOrder4 = root4.toArray();
		String[] preOrder4 = root4.toArray_preOrder();
		  myAssert( inOrder3.length == inOrder2.length);
		  myAssert(preOrder3.length == inOrder2.length);
		  myAssert( inOrder4.length == inOrder2.length);
		  myAssert(preOrder4.length == inOrder2.length);

		for (int i=0; i<count; i++)
		{
			myAssert( inOrder3[i] == inOrder2[i]);
			myAssert(preOrder3[i] == inOrder2[i]);
			myAssert( inOrder4[i] == inOrder2[i]);
			myAssert(preOrder4[i] == inOrder2[count-1-i]);
		}

	
	
		System.out.println("RANDOM UNIT TEST *** PASS ***");
	}
	public static String randomString(Random rand)
		throws IOException
	{
		char[] arr = new char[4+rand.nextInt(12)];

		for (int i=0; i<arr.length; i++)
		{
			int c64 = rand.nextInt(64);

			if (c64 < 26)
				arr[i] = (char)('a' + c64);
			else if (c64 < 52)
				arr[i] = (char)('A' + c64-26);
			else if (c64 < 62)
				arr[i] = (char)('0' + c64-52);
			else if (c64 == 62)
				arr[i] = '_';
			else
				arr[i] = '@';
		}

		return new String(arr);
	}



	public static void sortInput()
		throws IOException
	{
		// note that we call in.next() without first checking to
		// see if anything exists.  If you put *NOTHING* on stdin,
		// then you'll get an exception there.  Oh, well.  I can
		// live with that.
		Scanner in   = new Scanner(System.in);
		BSTNode root = new BSTNode(in.next());


		// read everything from the input
		while (in.hasNext())
		{
			// the input might have duplicates...so we have to
			// handle that situation.
			String word = in.next();

			try {
				insertHelper(root, word);
			} catch (IllegalArgumentException e) {
				System.err.printf("Duplicate word '%s' ignored.\n", word);
			}
		}
		writeDotFile(root, "proj1_sort.dot");


		// ideally, we'd have an iterator, but I didn't require
		// the students to implement one.  So an ugly "whole
		// array in one drop" algorithm it is!
		for (String word: root.toArray())
			System.out.println(word);
	}



	private static boolean debug_mode  = false;
	private static int     debug_count = 0;
	public static void insertHelper(BSTNode node, String val)
		throws IOException
	{
		node.insert(val);

		if (debug_mode)
		{
			String filename = String.format("proj1_DEBUG_%04d.dot", debug_count);
			debug_count++;

			writeDotFile(node, filename);
		}
	}



	public static void writeDotFile(BSTNode node, String filename)
		throws IOException
	{
		Writer out = new FileWriter(filename);
		out.write(node.toDotFile(true));
		out.close();
	}



	// I really ought to learn JUnit.  :(
	public static void myAssert(boolean condition)
		throws IOException
	{
		// this is a total hack, but I'm not the only one doing it.
		//
		// http://stackoverflow.com/questions/5558731/how-to-programmatically-enable-assert

		if (condition == false)
			throw new IllegalArgumentException("Assertion Failed");
	}
}
