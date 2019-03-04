package edu.uprm.ece.icom4035.polynomial;

import java.util.Iterator;

import edu.uprm.ece.icom4035.list.List;
import edu.uprm.ece.icom4035.list.ListFactory;

public class PolynomialImp extends TermListFactory implements Polynomial {
	
	@SuppressWarnings("static-access")
	ListFactory<Term> fact = new TermListFactory().newListFactory();
	private List<Term> list;
	
	
	public PolynomialImp(String string) {
		this.list = fact.newInstance();
		listMkr(string);
		
	}
	
	public PolynomialImp() {
		//Makes a quick list
		this.list = fact.newInstance();
		
}

	public void listMkr(String string) {
		String [] ply = string.split("\\+");
		double Coeff = 0;
		int Exp = 0;
		
		for(String p: ply) {
			
			if(p.contains("x^")) {
				if(p.startsWith("x")) {
					Coeff = 1;
					Exp = Integer.valueOf(p.substring(p.indexOf("^")+1, p.length()));
				}else {
					Coeff = Double.valueOf(p.substring(0,p.indexOf("x")));
					Exp = Integer.valueOf(p.substring(p.indexOf("^")+1, p.length()));
				}
			}else if(p.contains("x")) {
				 if(p.startsWith("x")) {
						Coeff = 1;
						Exp = 1;
					}else {
						Coeff = Double.valueOf(p.substring(0,p.indexOf("x")));
				 		Exp = 1;
					}
			}else {
				Coeff = Double.valueOf(p);
				Exp = 0;
			}
				
			this.list.add(new TermImp(Coeff, Exp));	
		}
	}
	
	
	
	@Override
	public Iterator<Term> iterator() {
		return list.iterator();
	}

	@Override
	public Polynomial add(Polynomial P2) {
		
		PolynomialImp APoly = new PolynomialImp();
		
		
		for(Term p :  this) { 
			APoly.list.add(new TermImp(p.getCoefficient(), p.getExponent()));
		}
		for(Term s : P2) { 
			boolean padded = false; 
			for(Term p: this.list) { 
				if(p.getExponent() == s.getExponent()) {
					((TermImp) APoly.list.get(this.list.firstIndex(p))).setCoefficient(p.getCoefficient() + s.getCoefficient());
					padded = true; 
				}
			}
			if(!padded) APoly.list.add(s);
		}
		PolynomialImp.insertionSort(APoly); 
		return APoly;
	}
	
	
	@Override
	public Polynomial subtract(Polynomial P2) {
		
		if(this.equals(P2)) { return new PolynomialImp("0"); }
		if(this.equals(new PolynomialImp("0"))) {  return new PolynomialImp(P2.multiply(-1.00).toString());  }
			
		return new PolynomialImp(this.add(P2.multiply(-1.00)).toString()); 
	}

	@Override
	public Polynomial multiply(Polynomial P2) {
		
		if(this.equals(new PolynomialImp("0")) || ((PolynomialImp) P2).equals(new PolynomialImp("0"))) {return new PolynomialImp("0");}
		
		
		List<Term> pres = this.fact.newInstance();
		
		for(Term p : this) { 
			for(Term s : P2) {
				pres.add(new TermImp(p.getCoefficient() * s.getCoefficient(), p.getExponent() + s.getExponent()));
			}
		}
		PolynomialImp.insertionSort(pres); 
		PolynomialImp preturn = new PolynomialImp(); 
		
		double coeff = 0;
		int exp = pres.first().getExponent();
		Iterator<Term> piter = pres.iterator(); 
		
		while(piter.hasNext()) {
			Term pterm = piter.next();
			
			if(pterm.getExponent() == exp) 
				
				coeff = coeff + pterm.getCoefficient(); 
			
			else { 
				
				preturn.list.add(new TermImp(coeff, exp));
				coeff = pterm.getCoefficient();
				exp = pterm.getExponent();
			}
			
			if(!(piter.hasNext())) preturn.list.add(new TermImp(coeff, exp));
		}		
		return preturn;
	}

	@Override
	public Polynomial multiply(double c) {
		
		if(c == 0.0) { return new PolynomialImp("0"); }
		PolynomialImp Poly = new PolynomialImp(); 
		for(Term p : this) {
			Poly.list.add(new TermImp(p.getCoefficient() * c, p.getExponent()));
		}
		return Poly;
	}

	@Override
	public Polynomial derivative() {
		PolynomialImp DPoly = new PolynomialImp();
		
		for(Term p:this) {
			
			if(p.getCoefficient()*p.getExponent() !=0) {
				DPoly.list.add(new TermImp(p.getCoefficient()*p.getExponent(),p.getExponent()-1));	
			}	
		}
		return DPoly;
	}

	@Override
	public Polynomial indefiniteIntegral() {
		
		PolynomialImp pres = new PolynomialImp();
		for(Term p : this) {
			pres.list.add(new TermImp(p.getCoefficient() / (p.getExponent() + 1), p.getExponent() + 1));
		}
		pres.list.add(new TermImp(1.0, 0)); 
		
		return pres;

		
	}

	@Override
	public double definiteIntegral(double a, double b) {
		
		double aresult = 0;
		double bresult = 0;
		double fresult = 0;
		
		aresult = this.indefiniteIntegral().evaluate(a);
		bresult = this.indefiniteIntegral().evaluate(b);
		
		fresult = bresult-aresult;
		return fresult;
		
	}

	@Override
	public int degree() {
		return this.list.first().getExponent();
	}

	@Override
	public double evaluate(double x) {
		double result = 0;
		for (Term p:this) { 
			result += p.evaluate(x);
			}
		return result;
	}

	@Override
	public boolean equals(Polynomial P) {
		if(this.list.size() != ((PolynomialImp)P).list.size()) { return false;  }
		for(int i = 0; i < this.list.size(); i++) {
			
			if(this.list.get(i).getCoefficient() != ((PolynomialImp)P).list.get(i).getCoefficient()) {
				return false;
			}else if(this.list.get(i).getExponent() != ((PolynomialImp)P).list.get(i).getExponent()) {
				return false;
			}
				
		}
		
		return true;
		
		
	}
	
	public String toString() {
		
		StringBuilder streturn = new StringBuilder();
		int count = 0;
		
		for(Term p: this) {
			if(count != 0) { streturn.append("+"); }
			count++;
			streturn.append(((TermImp) p).toString());
			
		}
		return streturn.toString();	
	}
	
	//Re:Used code from previous course
		public static void insertionSort(List<Term> terms) {
			for(int i = 0; i < terms.size(); i++) {
				if(terms.get(i).getCoefficient() == 0 && terms.size() - 1 != 0) {
					terms.remove(i);
				}
			}
			for (int i = 1; i < terms.size() - 1; i++) { 
				int j = i + 1;
				while ((j >= 1) && (terms.get(j).getExponent() > terms.get(j-1).getExponent())) {
					Term temp = terms.get(j);
					terms.set(j, terms.get(j - 1));
					terms.set(j-1, temp);
					j--;
				}
			}
	}
	
	
	public static void insertionSort(Polynomial polynomial) { insertionSort(((PolynomialImp) polynomial).list); }
	

}
