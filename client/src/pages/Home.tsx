import { Container, Separator, Switch } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { CarInfo, Category } from "../types/interfaces";
import CarCard from "../components/CarCard";
import CheckboxButton from "../components/CheckboxButton";

function Home() {
  const [cars, setCars] = useState<CarInfo[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [onlyAvailable, setOnlyAvailable] = useState<boolean>(false);
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
      <div className="pt-5 flex flex-col gap-4 items-start sm:flex-row sm:items-center">
        <div className="flex gap-2 items-center text-sm">
          <Switch onCheckedChange={() => setOnlyAvailable((prev) => !prev)} />
          <p>Only available</p>
        </div>
        <Separator orientation="vertical" className="hidden sm:block" />
        <div className="flex gap-2 items-center flex-wrap">
          {categories.map((category) => (
            <CheckboxButton
              key={category.id}
              name={category.name}
              checked={selectedCategories.includes(category)}
              handleClick={() => handleClick(category)}
            />
          ))}
        </div>
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
          .filter((car) => {
            if (onlyAvailable) {
              return car.status === "AVAILABLE";
            } else {
              return true;
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
