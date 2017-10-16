public class TuneListTest {
    /**
     * Expected result for first test: alphabetic order.
     */
    private static final String ALPH1 =
      "Deerhoof\nFlower\n0\nDeerhoof\nWe do Parties\n1\nFKA Twigs\nHours\n0\n";

    /**
     * Expected result for first test: popularity order.
     */
    private static final String POP1 =
      "Deerhoof\nWe do Parties\n1\nDeerhoof\nFlower\n0\nFKA Twigs\nHours\n0\n";

    /**
     * Expected result for second test: alphabetic order.
     */
    private static final String ALPH2 =
       "Deerhoof\nFlower\n1\nDeerhoof\nWe do Parties\n2\nFKA Twigs\nHours\n" +
       "0\nManu Chao\nLes Milles Paillettes\n0\nThe Necks\nRum Jungle\n3\n";

    /**
     * Expected result for second test: popularity order.
     */
    private static final String POP2 =
       "The Necks\nRum Jungle\n3\nDeerhoof\nWe do Parties\n2\n" +
       "Deerhoof\nFlower\n1\nFKA Twigs\nHours\n0\n" +
       "Manu Chao\nLes Milles Paillettes\n0\n";

    public static void main(String[] args) {

      String result; // will lold results of tests

      TuneList tl = new TuneList();
      tl.addTune("Deerhoof", "We do Parties");
      tl.addTune("FKA Twigs", "Hours");
      tl.likeTune("Deerhoof", "We do Parties");
      tl.addTune("Deerhoof", "Flower");
      tl.addTune("FKA Twigs", "Hours");  // duplicate shouldn't be added
      tl.likeTune("No Such Name", "No Such Title");  // not there to like

      // First test: should give same results as in Maude
      result = tl.listAlphabetically();
      System.out.println(result);
      System.out.println("Same result as Maude: " + result.equals(ALPH1));
      result = tl.listByLikes();
      System.out.println(result);
      System.out.println("Same result as Maude: " + result.equals(POP1));

      tl.likeTune("Deerhoof", "Flower");
      tl.addTune("The Necks", "Rum Jungle");
      tl.likeTune("The Necks", "Rum Jungle");
      tl.likeTune("The Necks", "Rum Jungle");
      tl.likeTune("Deerhoof", "We do Parties");
      tl.addTune("Manu Chao", "Les Milles Paillettes");
      tl.likeTune("The Necks", "Rum Jungle");

      // second test: should give same results as in Maude
      result = tl.listAlphabetically();
      System.out.println(result);
      System.out.println("Same result as Maude: " + result.equals(ALPH2));
      result = tl.listByLikes();
      System.out.println(result);
      System.out.println("Same result as Maude: " + result.equals(POP2));
   }
}
