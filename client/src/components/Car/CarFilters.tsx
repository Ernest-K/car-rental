import { useEffect, useState } from "react";
import { Category } from "../../types/interfaces";
import { Separator, Switch } from "@radix-ui/themes";
import CheckboxButton from "../CheckboxButton";

interface CarFiltersProps {
  selectedCategories: Category[];
  onSelectedCategoriesChange: React.Dispatch<React.SetStateAction<Category[]>>;
  onlyAvailable: boolean;
  onOnlyAvailableChange: React.Dispatch<React.SetStateAction<boolean>>;
}

function CarFilters({
  selectedCategories,
  onSelectedCategoriesChange,
  onlyAvailable,
  onOnlyAvailableChange,
}: CarFiltersProps) {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/categories")
      .then((res) => res.json())
      .then((data) => setCategories(data));
  }, []);

  const handleClick = (category: Category) => {
    if (selectedCategories.includes(category)) {
      onSelectedCategoriesChange(
        selectedCategories.filter((c) => c !== category)
      );
    } else {
      onSelectedCategoriesChange([...selectedCategories, category]);
    }
  };

  return (
    <>
      <div className="flex gap-2 items-center text-sm">
        <Switch
          onCheckedChange={() => onOnlyAvailableChange((prev) => !prev)}
        />
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
    </>
  );
}

export default CarFilters;
