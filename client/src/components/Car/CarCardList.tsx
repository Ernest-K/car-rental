import { Car } from "../../types/interfaces";
import CarCard from "./CarCard";

interface CarCardListProps {
  cars: Car[];
}

function CarCardList({ cars }: CarCardListProps) {
  return cars.map((car) => <CarCard data={car} key={car.id} />);
}

export default CarCardList;
