import static choco.Choco.*;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.cp.model.CPModel;
import choco.kernel.model.Model;
import choco.kernel.solver.Solver;
import choco.cp.solver.CPSolver;

public class Sudoku {

	
	public static void afficheGrille(IntegerVariable[][] rows, Solver s){
		for(int i=0;i < 9;i++){
			for(int j=0;j < 9;j++){
				System.out.print(s.getVar(rows[i][j]).getVal()+" ");
				// toutes les trois colonnes on affiche une séparation verticale
				if((j + 1)%3==0 & j<8){System.out.print("| ");}
			}
			System.out.println();
			if((i + 1) % 3 == 0 & i < 8){
				// toutes les trois lignes on affiche une séparation horizontale
				System.out.println("----------------------");
			}
		}
	}
	
	public static void main(String[] args) {
		
		
		// Création d'un nouveau CSP
		Model m = new CPModel();
		 
		// Création des variables (cases de la grille)
		IntegerVariable[][] grille = makeIntVarArray("grille", 9, 9, 1, 9);
		 
		// Création et ajout des contraintes: toutes les cases d'une même ligne sont différentes entre elles
		for (int i = 0; i < 9; i++) {
		    for (int j = 0; j < 9; j++) {
		    	for (int k = j; k < 9; k++) {
		    		if (k != j) { m.addConstraint(neq(grille[i][j], grille[i][k]));}
		    	}
		    }
		}
		
		// Création et ajout des contraintes: toutes les cases d'une même colonne sont différentes entre elles
		for (int j = 0; j < 9; j++) {
		    for (int i = 0; i < 9; i++) {
		    	for (int k = i; k < 9; k++) {
		    		if (k != i) {m.addConstraint(neq(grille[i][j], grille[k][j]));}
		    	}
		    }
		}
		// Création et ajout des contraintes: toutes les cases d'un même sous-carré sont différentes entre elles
		for (int ci = 0; ci < 9; ci += 3) { 
		    for (int cj = 0; cj < 9; cj += 3) {
		    // (ci,cj) sont les coordonnées de la première case de chaque carré
		    	for (int i = ci; i < ci + 3; i++) {
		    		for (int j = cj; j < cj + 3; j++) {
		    			// (i,j) parcourent toutes les cases d'un carré
		    				for (int k = ci; k < ci + 3; k++) {
		    					for (int l = cj; l < cj + 3; l++) {
		    						//(k,l) parcourent toutes les cases d'un carré
		    						if (k != i || l != j) m.addConstraint(neq(grille[i][j], grille[k][l]));
		    					}
		    				}
		    		}
		    	}
		    }
		}
		 
		 
		// Construction d'un nouveau solveur
		Solver s = new CPSolver();
		 
		// le solveur lit le CSP précédemment construit
		s.read(m);
		 
		// le solveur résoud le CSP précédemment lu
		s.solve();
		
		// affichage de la grille résolue
		afficheGrille(grille, s);

	}

}
