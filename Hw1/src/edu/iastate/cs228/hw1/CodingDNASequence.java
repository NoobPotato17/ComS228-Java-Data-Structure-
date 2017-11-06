package edu.iastate.cs228.hw1;

/**
 * This class extends the class DNASequence. A coding sequence is obtained by
 * concatenating the coding exons in the same order as they occur in the genomic
 * sequence.The coding sequence, which often begins with the three letters ATG
 * (called a start codon), is translated into a protein sequence by taking every
 * group of three consecutive letters called a codon and replacing it by a
 * corresponding amino acid according to the genetic code
 * 
 * @author Ram Luitel
 */
public class CodingDNASequence extends DNASequence {

	/**
	 * Constructor. This constructor takes array of character
	 * 
	 * @param cdnaarr
	 *            the character array code DNA sequence
	 */
	public CodingDNASequence(char[] cdnaarr) {
		super(cdnaarr);
	}

	/**
	 * Check either it has start condon or not
	 * 
	 * @return true if it has start condon otherwise false
	 */
	public boolean checkStartCodon() {
		if (super.getSeq().length < 3)
			return false;
		else if ((super.getSeq()[0] == 'a' || super.getSeq()[0] == 'A')
				&& (super.getSeq()[1] == 't' || super.getSeq()[1] == 'T')
				&& (super.getSeq()[2] == 'g' || super.getSeq()[2] == 'G'))
			return true;
		else
			return false;
	}

	/**
	 * The method throws an RuntimeException exception with the message “No
	 * start codon” if a call to the method checkStartCodon() returns false.
	 * Otherwise, the method uses the private method translates the coding
	 * sequence in the character array seqarr into a protein sequence by calling
	 * the private method getAminoAcid on every codon in the coding sequence.
	 * 
	 * @return proteinSequence the character array of protein Sequence
	 */
	public char[] translate() {
		// the length of resulting character array will be three time less then the original 
		// array because every three characters is translate to single character.
		char proteinSequence[] = new char[super.seqLength() / 3]; 
		if (!checkStartCodon())
			throw new RuntimeException("No start codon");
		else {
			char temp[] = super.getSeq();
			int charRemain = temp.length % 3;
			String seq = "";
			int j = 0;
			for (int i = 0; i <= temp.length - charRemain - 3; i = i + 3, j++) {
				seq = String.copyValueOf(temp, i, 3);
				if (getAminoAcid(seq) == '$')
					break;
				else
					proteinSequence[j] = getAminoAcid(seq);
			}
		}
		return proteinSequence;
	}

	/**
	 * This is given method which is used to write translate method
	 * 
	 * @param codon the string of three character that is translate to Coding
	 *            sequence
	 * @return the single character that represent the string of three character
	 */
	private char getAminoAcid(String codon) {
		if (codon == null)
			return '$';
		char aa = '$';
		switch (codon.toUpperCase()) {
		case "AAA":
			aa = 'K';
			break;
		case "AAC":
			aa = 'N';
			break;
		case "AAG":
			aa = 'K';
			break;
		case "AAT":
			aa = 'N';
			break;

		case "ACA":
			aa = 'T';
			break;
		case "ACC":
			aa = 'T';
			break;
		case "ACG":
			aa = 'T';
			break;
		case "ACT":
			aa = 'T';
			break;

		case "AGA":
			aa = 'R';
			break;
		case "AGC":
			aa = 'S';
			break;
		case "AGG":
			aa = 'R';
			break;
		case "AGT":
			aa = 'S';
			break;

		case "ATA":
			aa = 'I';
			break;
		case "ATC":
			aa = 'I';
			break;
		case "ATG":
			aa = 'M';
			break;
		case "ATT":
			aa = 'I';
			break;

		case "CAA":
			aa = 'Q';
			break;
		case "CAC":
			aa = 'H';
			break;
		case "CAG":
			aa = 'Q';
			break;
		case "CAT":
			aa = 'H';
			break;

		case "CCA":
			aa = 'P';
			break;
		case "CCC":
			aa = 'P';
			break;
		case "CCG":
			aa = 'P';
			break;
		case "CCT":
			aa = 'P';
			break;

		case "CGA":
			aa = 'R';
			break;
		case "CGC":
			aa = 'R';
			break;
		case "CGG":
			aa = 'R';
			break;
		case "CGT":
			aa = 'R';
			break;

		case "CTA":
			aa = 'L';
			break;
		case "CTC":
			aa = 'L';
			break;
		case "CTG":
			aa = 'L';
			break;
		case "CTT":
			aa = 'L';
			break;

		case "GAA":
			aa = 'E';
			break;
		case "GAC":
			aa = 'D';
			break;
		case "GAG":
			aa = 'E';
			break;
		case "GAT":
			aa = 'D';
			break;

		case "GCA":
			aa = 'A';
			break;
		case "GCC":
			aa = 'A';
			break;
		case "GCG":
			aa = 'A';
			break;
		case "GCT":
			aa = 'A';
			break;

		case "GGA":
			aa = 'G';
			break;
		case "GGC":
			aa = 'G';
			break;
		case "GGG":
			aa = 'G';
			break;
		case "GGT":
			aa = 'G';
			break;

		case "GTA":
			aa = 'V';
			break;
		case "GTC":
			aa = 'V';
			break;
		case "GTG":
			aa = 'V';
			break;
		case "GTT":
			aa = 'V';
			break;

		case "TAA":
			aa = '$';
			break;
		case "TAC":
			aa = 'Y';
			break;
		case "TAG":
			aa = '$';
			break;
		case "TAT":
			aa = 'Y';
			break;

		case "TCA":
			aa = 'S';
			break;
		case "TCC":
			aa = 'S';
			break;
		case "TCG":
			aa = 'S';
			break;
		case "TCT":
			aa = 'S';
			break;

		case "TGA":
			aa = '$';
			break;
		case "TGC":
			aa = 'C';
			break;
		case "TGG":
			aa = 'W';
			break;
		case "TGT":
			aa = 'C';
			break;

		case "TTA":
			aa = 'L';
			break;
		case "TTC":
			aa = 'F';
			break;
		case "TTG":
			aa = 'L';
			break;
		case "TTT":
			aa = 'F';
			break;
		default:
			aa = '$';
			break;
		}
		return aa;
	}
}
