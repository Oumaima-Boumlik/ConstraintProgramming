import static choco.Choco.*;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.cp.model.CPModel;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.solver.Solver;
import choco.cp.solver.CPSolver;

public class ColoringGraph{

	public static void main(String[] args) {
		//Declaration des Variable/Domaines
		String couleurs[] = {"bleu","rouge","jaune","vert"};
		int domX1[] = {0,3,2,1};
		int domX2[] = {0,2,1};
		int domX3[] = {1,3,2};
		int domX4[] = {2,3,0,1};
		
		IntegerVariable x1 = makeIntVar("x1",domX1);
		IntegerVariable x2 = makeIntVar("x2",domX2);
		IntegerVariable x3 = makeIntVar("x3",domX3);
		IntegerVariable x4 = makeIntVar("x4",domX4);
		//Declaration du modele
		Model monCSP = new CPModel();
        //Ajout des Contraintes
		Constraint cX1X2 = neq(x1,x2);
		Constraint cX1X4 = neq(x1,x4);
		Constraint cX1X3 = neq(x1,x3);
		Constraint cX2X4 = eq(x2,x4);
		Constraint cX4X3 = eq(x4,x3);
		
		monCSP.addConstraint(cX1X2);
		monCSP.addConstraint(cX1X4);
		monCSP.addConstraint(cX1X3);
		monCSP.addConstraint(cX2X4);
		monCSP.addConstraint(cX4X3);
		
		
		//Resolution
		Solver s = new CPSolver();
		s.read(monCSP);
		s.solve();
		
		//Affichage des resultats
		do{
		System.out.println("X1="+couleurs[s.getVar(x1).getVal()]);
		System.out.println("X2="+couleurs[s.getVar(x2).getVal()]);
		System.out.println("X3="+couleurs[s.getVar(x3).getVal()]);
		System.out.println("X4="+couleurs[s.getVar(x4).getVal()]);
		System.out.println();
		} while(s.nextSolution());

	}

}
