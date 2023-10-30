import { Container } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { CarInfo, Category } from "../types/interfaces";
import CarCard from "../components/CarCard";
import ButtonCheckbox from "../components/ButtonCheckbox";

function Home() {
  const [cars, setCars] = useState<CarInfo[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategories, setSelectedCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/categories")
      .then((res) => res.json())
      .then((data) => setCategories(data));

    fetch("http://localhost:8080/api/cars")
      .then((res) => res.json())
      .then((data) => setCars(data));
  }, []);

  const handleClick = (category: Category) => {
    if (selectedCategories.includes(category)) {
      setSelectedCategories(selectedCategories.filter((c) => c !== category));
    } else {
      setSelectedCategories([...selectedCategories, category]);
    }
  };

  return (
    <Container className="px-4">
      <div className="pt-5">
        {categories.map((category) => (
          <ButtonCheckbox
            key={category.id}
            name={category.name}
            checked={selectedCategories.includes(category)}
            handleClick={() => handleClick(category)}
          />
        ))}
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 py-5">
        {cars
          .filter((car) => {
            if (selectedCategories.length == 0) {
              return true;
            } else {
              return selectedCategories
                .map((category) => category.name)
                .includes(car.category.name);
            }
          })
          .map((car) => (
            <CarCard data={car} key={car.id} />
          ))}
      </div>
    </Container>
  );
}

export default Home;
