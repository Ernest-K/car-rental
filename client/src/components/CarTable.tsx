import { Car } from "@/types/interfaces";
import {
  CheckCircledIcon,
  CrossCircledIcon,
  Pencil2Icon,
  TrashIcon,
} from "@radix-ui/react-icons";
import { Button, Table } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "./ui/use-toast";

function CarTable() {
  const [cars, setCars] = useState<Car[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/cars")
      .then((res) => res.json())
      .then((data) => setCars(data));
  }, []);

  const deleteCar = (id: number) => {
    fetch(`http://localhost:8080/api/cars/${id}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (res.ok) {
          return res.text();
        }
      })
      .then((data) => {
        setCars(cars.filter((car) => car.id != id));
        toast({
          title: "Reservation",
          description: data,
        });
      });
  };

  return !cars.length ? (
    <p className="text-center p-3">No cars</p>
  ) : (
    <Table.Root className="m-3" variant="surface">
      <Table.Header>
        <Table.Row>
          <Table.ColumnHeaderCell>Id</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Status</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Make</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Model</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Category</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell className="text-center">
            Operation
          </Table.ColumnHeaderCell>
        </Table.Row>
      </Table.Header>

      <Table.Body>
        {cars.map((car) => (
          <Table.Row key={car.id}>
            <Table.RowHeaderCell>{car.id}</Table.RowHeaderCell>
            <Table.Cell className="flex items-center">
              {car.status === "UNAVAILABLE" ? (
                <CrossCircledIcon className="w-4 h-4 mr-1 text-red-600" />
              ) : (
                <CheckCircledIcon className="w-4 h-4 mr-1 text-green-600" />
              )}
              <p className="capitalize">{car.status.toLowerCase()}</p>
            </Table.Cell>
            <Table.Cell>{car.make}</Table.Cell>
            <Table.Cell>{car.model}</Table.Cell>
            <Table.Cell>{car.category && car.category.name}</Table.Cell>
            <Table.Cell>
              <div className="w-full flex justify-evenly">
                <Button size={"3"} variant="ghost">
                  <Link to={`/dashboard/car/${car.id}/edit`}>
                    <Pencil2Icon width="16" height="16" />
                  </Link>
                </Button>
                <Button
                  size={"3"}
                  variant="ghost"
                  color="red"
                  onClick={() => deleteCar(car.id)}
                >
                  <TrashIcon width="16" height="16" />
                </Button>
              </div>
            </Table.Cell>
          </Table.Row>
        ))}
      </Table.Body>
    </Table.Root>
  );
}

export default CarTable;
