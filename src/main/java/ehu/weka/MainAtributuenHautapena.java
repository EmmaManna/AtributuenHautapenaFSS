package ehu.weka;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.core.Instances;

import java.io.FileWriter;

public class MainAtributuenHautapena {
    public static void main(String[] args) throws Exception {
        if (args.length != 6) {
            System.out.println("Atazak:");
            System.out.println("\t 1. Gainbegiratutako datuak emanda, eredu optimoa lortu. ");
            System.out.println("\t 2. Eredua aplikatu etiketatuta ez dagoen test multzoari (test blind) eta iragarpenak lortu. ");
            System.out.println("\t 3. Aurre-prozesua: Train/test multzoak missing values daukate? Egokia litzateke galdutako balioak inputatzea? " +
                    "\n\tErrepikatu prozesua inputatutako datuekin eta emaitzak aztertu. ");

            System.out.println("\nArgumentuak:");
            System.out.println("\t 1. Datu sortaren kokapena (path) .arff  formatuan (input). Aurre-baldintza: klasea azken atributuan egongo da.");
            System.out.println("\t 2. NB.model: eredua gordetzeko path");
            System.out.println("\t 3. Blind-test sortaren kokapena (path) .arff  formatuan (input).");
            System.out.println("\t 4. predictions.txt: iragarpenak gordetzeko path");
            System.out.println("\t 5. aurre.model: aurre-prozesua aplikatu ostean eredua gordetzeko path");
            System.out.println("\t 6. predictionsAurre.txt: aurre-prozesua aplikatu ostean iragarpenak gordetzeko path");
            System.exit(0);
        }

        AtributuenHautapena fss = AtributuenHautapena.getInstance();
        Instances data = fss.datuakKargatu(args[0]);

        FileWriter fw1 = new FileWriter(args[3]);


        //1. Eredu iragarle optimoa sortu eta gorde
        Instances selection = fss.selection(data);
        Evaluation holdOut = fss.holdOut(selection,70);

        System.out.println("Atributu kopurua: "+selection.numAttributes());
        System.out.println("\n"+holdOut.toMatrixString()+"\n");
        System.out.println("F-score: "+holdOut.weightedFMeasure());

        Classifier cls = fss.sailkatzailea();
        cls.buildClassifier(data);
        fss.serialization(args[1],cls);


        //2. Test multzoaren iragarpenak egin
        Instances test = fss.datuakKargatu(args[2]);
        Classifier model = fss.deserialize(args[1]);

        if(!data.equalHeaders(test)){
            System.out.println("Test-multzoa ez da eredu iragarlearekiko bateragarria.");
            System.exit(0);
        }

        Evaluation eval = new Evaluation(test);
        eval.evaluateModel(model,test);

        fw1.write("Exekuzio data: "+java.time.LocalDateTime.now().toString()+"\n");
        fw1.write("\n-- Test Set -- \n");
        fw1.write("Instantzia\tActual\tPredicted Errorea\n");
        for(int i=0; i< eval.predictions().size();i++){
            double actual = eval.predictions().get(i).actual();
            double predicted = eval.predictions().get(i).predicted();

            fw1.write("\t"+(i+1)+"\t"+test.instance(i).stringValue(test.classIndex())+ "\t"+test.attribute(test.classIndex()).value((int) predicted));

            if(actual!=predicted && !Double.isNaN(actual)){
                fw1.write("\t     +");
            }
            fw1.write("\n");
        }


        //3. Aurre-prozesua:



        fw1.close();
    }
}
