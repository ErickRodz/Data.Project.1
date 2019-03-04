
package edu.uprm.ece.icom4035.polynomial;

public class TermImp implements Term{
		public int exponent;
		public double coefficient;
		
		
		public TermImp(Double Coeff, int Exp) {
			this.coefficient = Coeff;
			this.exponent = Exp;
		}

	@Override
	public double getCoefficient() {return this.coefficient;}
	public void setCoefficient(double coeff) {this.coefficient = coeff;}
	
	@Override
	public int getExponent() {return this.exponent;}
	public void setExponent(int exp) {this.exponent = exp;}

	@Override
	public double evaluate(double x) {return (coefficient * Math.pow(x,  exponent));}
	
	
	//Redo this 
	public String toString() {
		double coeff = coefficient;
		int exp = exponent;
		StringBuilder streturn = new StringBuilder();
		
			if(coeff > 1 || coeff < 0) {  streturn.append(String.format("%.2f", coeff));  
				if(exp > 1) { streturn.append("x^"); streturn.append(Integer.toString(exp)); 
				}else if(exp == 1) { streturn.append("x"); }
			}else { 
				if(exp > 1) {  streturn.append("x^"); streturn.append(Integer.toString(exp)); 
				
			}
			else if(exp == 1)  streturn.append("x"); 
				else streturn.append(String.format("%.2f", coeff)); 
			}
			
		return streturn.toString();
		
		
	}

}
