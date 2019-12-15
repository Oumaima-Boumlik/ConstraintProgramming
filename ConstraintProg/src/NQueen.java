import static choco.Choco.*;
import choco.cp.model.CPModel;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.solver.Solver;
import choco.cp.solver.CPSolver;

public class NQueen {
	
	public static void main(String[] args) {
   
		int n = 10; 
		Model m = new CPModel();
		 
		// Les variables
		IntegerVariable[][] lignes = makeIntVarArray("Q", n,n, 0,1);
		IntegerVariable[][] colonnes = makeIntVarArray("Q", n,n, 0,1);
		
		for (int i = 0; i < n; i++) {
		    for (int j = 0; j < n; j++) {
		    	m.addConstraint(eq(colonnes[i][j],lignes[j][i]));
		    }
		}
		 
		// Les contraintes
		for(int i = 0; i < n; i++){
			m.addConstraint(eq(sum(colonnes[i]),1));
			m.addConstraints(eq(sum(lignes[i]),1));
		}

		
		for(int i = 0;i < n;i++)
			for(int j = 0;j < n;j++){
				IntegerExpressionVariable diag1 = constant(0); 
				IntegerExpressionVariable diag2 = constant(0);
				// diagonale 1
				int k = i+1;
				int l = j+1;
				while(k < n & l < n){
					diag1 = plus(diag1,lignes[k][l]);
					k++;
					l++;
				}
				k = i - 1;
				l = j - 1;
				while(k >= 0 & l >= 0){
					diag1 = plus(diag1,lignes[k][l]);
					k--;
					l--;
				}
				m.addConstraint(leq(diag1,1));
				// diagonale 2
				k = i - 1;
				l = j + 1;
				while(k >= 0 & l < n){
					diag2 = plus(diag2,lignes[k][l]);
					k--;
					l++;
					}
				k = i + 1;
				l = j - 1;
				while(k < n & l >= 0){
					diag2 = plus(diag2,lignes[k][l]);
					k++;
					l--;
				}
				m.addConstraint(leq(diag2,1));
			}
				
				

		// Création du solver & Resolution
		Solver s = new CPSolver();
		s.read(m);
		s.solve();
		int i = 1; 
		
		//Affichage des solutions
		do{
		System.out.println("Solution "+i);
		afficheEchiquier(lignes,s);
		System.out.println();
		i++;
		
		}while(s.nextSolution());
		
}
	
	public static void afficheEchiquier(IntegerVariable[][] e,Solver s){
		for (int i = 0; i < e.length; i++) {
		    for (int j = 0; j < e[i].length; j++) {
		    	if(s.getVar(e[i][j]).getVal()==0) System.out.print(" _ ");
		    	else System.out.print(" X ");
		    }
		    System.out.println();
		}
	}
}
