import java.util.Date;


public class NuclideVectorElement {
	@Override
	public String toString() {
		return "" + nuclide + "\t" 
				+ referenceNuclide + "\t" 
				+ ratio + "\t" 
				+  date;
	}

	public String nuclide;
	public String referenceNuclide;
	public float ratio;
	public Date date;
	
	public NuclideVectorElement()
	{
		
	}

	public NuclideVectorElement(String nuclide, String referenceNuclide,
			float ratio, Date date) {
		super();
		this.nuclide = nuclide;
		this.referenceNuclide = referenceNuclide;
		this.ratio = ratio;
		this.date = date;
	}
}
