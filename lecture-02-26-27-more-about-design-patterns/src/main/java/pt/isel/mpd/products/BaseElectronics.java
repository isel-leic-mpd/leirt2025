package pt.isel.mpd.products;

public abstract class BaseElectronics implements Electronics {

        private final String name;
        private final String brand;
        private final double price;

        protected BaseElectronics(String name, String brand, double price)  {
            this.name = name; this.brand = brand; this.price = price;
        }

        // Product implementation

        @Override
        public String getName()             { return this.name;      }

        @Override
        public double getPrice()            { return this.price;     }

        @Override
        public String  getBrand()           { return brand;     }


        public String toString() {
            String s = String.format("%s %s: price %.2f euros",brand, name,price);
            return s;

        }

    

}
