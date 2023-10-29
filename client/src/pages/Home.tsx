import { Container } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { CarInfo } from "../types/interfaces";
import CarCard from "../components/CarCard";

function Home() {
  const [cars, setCars] = useState<CarInfo[]>([]);
  useEffect(() => {
    fetch("http://localhost:8080/api/cars")
      .then((res) => res.json())
      .then((data) => setCars(data));
  }, []);

  return (
    <Container className="px-4">
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 py-5">
        {cars.map((car) => (
          <CarCard data={car} key={car.id} />
        ))}
      </div>
    </Container>
  );
}

export default Home;
