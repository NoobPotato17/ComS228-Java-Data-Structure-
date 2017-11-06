package edu.iastate.cs228.hw1;

/**
 * This class extends the class DNASequence. The class GenomicDNASequence has a
 * constructor and a method, but has no additional field. A genomic DNA sequence
 * often codes a gene with separate protein-coding parts called exons, with
 * parts between/before/after them called introns / intergenic regions.
 * 
 * @author Ram Luitel
 */
public class GenomicDNASequence extends DNASequence {
	/**
	 * Constructor
	 * 
	 * @param gdnaarr
	 *            the character array of Genomic DNA sequence
	 */
	public GenomicDNASequence(char[] gdnaarr) {
		super(gdnaarr);
	}

	/**
	 * This the method takes all the coding exons specified by the array
	 * exonpos, concatenates them in order, and returns the resulting sequence
	 * in a new character array
	 * 
	 * @param exonpos
	 *            the integer array
	 * @return sequence the character array
	 */
	public char[] extractExons(int[] exonpos) {
		if (exonpos.length == 0 || exonpos.length % 2 == 1)
			throw new IllegalArgumentException("Empty array or odd number of array elements");
		int counter = 0;
		for (int i = 0; i < exonpos.length; i += 2) {
			for (int j = exonpos[i]; j < exonpos[i + 1]; j++) {
				counter++;
			}
		}
		char sequence[] = new char[counter];
		int x = 0;
		for (int i = 0; i < exonpos.length; i += 2) {
			if (exonpos[i] < 0 || exonpos[i] >= super.seqLength())
				throw new IllegalArgumentException("Exon position is out of bound");

			if (exonpos[i] > exonpos[i + 1])
				throw new IllegalArgumentException("Exon positions are not in order");

			for (int j = exonpos[i]; j < exonpos[i + 1]; j++,x++) {
				sequence[x] = super.getSeq()[j];
				
			}
		}
		return sequence;
	}

}
