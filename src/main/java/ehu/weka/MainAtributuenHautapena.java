package ehu.weka;

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
    }
}
