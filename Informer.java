public class Informer extends Villager {
    public Informer(String name) {
        super(name, Role.informer);
    }


    public String informerFinalize(Game god) {
        String result = "" ;
        int number = (int)(Math.random() * (4 - 1 + 1) + 1) ;
        switch (number) {
            case 1 :
                result += "There is a mafia who’s name starts with " ;
                for (int i = 0; i < god.mafias.size(); i++) {
                    if(god.mafias.get(i).isAlive() && !((Mafia)god.mafias.get(i)).isInformed()) {
                        result += god.mafias.get(i).getName().charAt(0);
                        break;
                    }
                }
                break;
            case 2 :
                if(god.getDayIterator() != 1) {
                    result += randomName();
                    result += " was voted to be killed";
                    break;
                }
            case 3 :
                result += "Number of alive mafia : " + god.getMafiaNumber() ;
                break;
            case 4 :
                result += "There is a joker who’s name starts with " + god.getJoker().getName().charAt(0) ;
                break;
        }
        return result;
    }

    private String randomName() {
        String result = "" ;
        String container = "" ;
        int iterate = 0 ;
        while(iterate < Mafia.voteeNames.size()) {
            int number = (int)(Math.random() * ((Mafia.voteeNames.size() -1) - 0 + 1) + 0) ;
            if(!container.contains(Integer.toString(number))) {
                container += number ;
                iterate++ ;
            }
            if(Mafia.voteeNames.get(number).isAlive()) {
                result += Mafia.voteeNames.get(number).getName().charAt(0) ;
                break;
            }
        }
        return result ;
    }
}
