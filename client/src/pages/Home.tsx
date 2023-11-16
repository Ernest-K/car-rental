import { Container } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { Car, Category } from "../types/interfaces";
import CarCardList from "../components/car/CarCardList";
import CarFilters from "../components/car/CarFilters";

function Home() {
  const [cars, setCars] = useState<Car[]>([]);
  const [onlyAvailable, setOnlyAvailable] = useState<boolean>(false);
  const [selectedCategories, setSelectedCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/cars")
      .then((res) => res.json())
      .then((data) => setCars(data));
  }, []);

  const filteredCars = cars
    .filter((car) => {
      if (selectedCategories.length == 0) {
        return true;
      } else {
        return selectedCategories
          .map((category) => category.name)
          .includes(car.category && car.category.name);
      }
    })
    .filter((car) => {
      if (onlyAvailable) {
        return car.status === "AVAILABLE";
      } else {
        return true;
      }
    });

  return (
    <Container className="px-4">
      <div className="pt-5 flex flex-col gap-4 items-start sm:flex-row sm:items-center">
        <CarFilters
          selectedCategories={selectedCategories}
          onSelectedCategoriesChange={setSelectedCategories}
          onlyAvailable={onlyAvailable}
          onOnlyAvailableChange={setOnlyAvailable}
        />
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 py-5">
        <CarCardList cars={filteredCars} />
      </div>
    </Container>
  );
}

export default Home;
