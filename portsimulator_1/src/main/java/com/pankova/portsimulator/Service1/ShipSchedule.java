package com.pankova.portsimulator.Service1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ShipSchedule {
        private final Random RND = new Random();

        private List<Ship> schedule;

        public ShipSchedule() {
                schedule = new LinkedList<>();
        }

        //generating ship schedule with sorted arrival ships time
        public void  generateSchedule(int shipCount) {
                for (int i = 0; i < shipCount; ++i) {
                        Calendar date = new GregorianCalendar(
                                2021,
                                6,
                                RND.nextInt(31) + 1,
                                RND.nextInt(24),
                                RND.nextInt(60),
                                RND.nextInt(60));

                        CargoType cargoType = CargoType.values()[RND.nextInt(CargoType.values().length)];

                        int cargoWeight = switch (cargoType) {
                                case DRY ->  (RND.nextInt(Ship.MAX_DRY_CARGO_WEIGHT - Ship.MAX_DRY_CARGO_WEIGHT / 3) + Ship.MAX_DRY_CARGO_WEIGHT / 3);
                                case LIQUID -> (RND.nextInt(Ship.MAX_LIQUID_CARGO_WEIGHT - Ship.MAX_LIQUID_CARGO_WEIGHT / 3) + Ship.MAX_LIQUID_CARGO_WEIGHT / 3);
                                case CONTAINER -> (RND.nextInt(Ship.MAX_CONTAINER_CARGO_WEIGHT - Ship.MAX_CONTAINER_CARGO_WEIGHT / 3) + Ship.MAX_CONTAINER_CARGO_WEIGHT / 3);
                        };

                        Ship ship = new Ship(date, Integer.toString(i), cargoType, cargoWeight);
                        schedule.add(ship);
                }

                schedule.sort(Ship.shipDateComparator);
                printSchedule();
        }

        public void inputShipSchedule() throws ParseException, InputMismatchException {
                try (Scanner in = new Scanner(System.in)){

                System.out.println("Enter ship's arrival date in dd HH:mm:ss format: ");
                Calendar date = new GregorianCalendar();
                date.setTime(new SimpleDateFormat("dd HH:mm:ss").parse(in.nextLine()));
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, 6);

                System.out.println("Enter ship's name number: ");
                int nameNumber = in.nextInt();

                for (CargoType ct : CargoType.values()) {
                        System.out.println(ct.name() + " = " + ct.ordinal());
                }
                System.out.println("Enter ship's cargo type number: ");
                CargoType cargoType = switch (in.nextInt()) {
                        case 0 -> CargoType.DRY;
                        case 1 -> CargoType.LIQUID;
                        case 2 -> CargoType.CONTAINER;
                        default -> throw new InputMismatchException();
                };

                final int MAX_WEIGHT = switch (cargoType) {
                        case DRY -> Ship.MAX_DRY_CARGO_WEIGHT;
                        case LIQUID -> Ship.MAX_LIQUID_CARGO_WEIGHT;
                        case CONTAINER -> Ship.MAX_CONTAINER_CARGO_WEIGHT;
                };
                System.out.println("Enter ship's cargo weight in range [0, " + MAX_WEIGHT + "]: ");
                int cargoWeight = in.nextInt();
                if ((cargoWeight < 0) || (cargoWeight > MAX_WEIGHT)) {
                        throw new InputMismatchException();
                }

                Ship ship = new Ship(date, Integer.toString(nameNumber), cargoType, cargoWeight);
                schedule.add(ship);
                schedule.sort(Ship.shipDateComparator);
                printSchedule();
        }
        }

        public void printSchedule() {
                for (Ship s : schedule) {
                        System.out.println(s);
                }
        }

        public List<Ship> getSchedule() {
                return schedule;
        }

        public void setSchedule(List<Ship> schedule) {
                this.schedule = schedule;
        }
}
