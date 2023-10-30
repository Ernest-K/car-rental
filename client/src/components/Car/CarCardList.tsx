import { CarInfo } from "../../types/interfaces";
import CarCard from "./CarCard";

interface CarCardListProps {
  cars: CarInfo[];
}

function CarCardList({ cars }: CarCardListProps) {
  return cars.map((car) => <CarCard data={car} key={car.id} />);
}

export default CarCardList;
