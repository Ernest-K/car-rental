import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { CarDetailInfo } from "../../types/interfaces";
import { AspectRatio, Button, Container, Table } from "@radix-ui/themes";
import ParameterTile from "../../components/ParameterTile";
import carImg from "../../assets/example.jpg";
import { ArrowLeftIcon } from "@radix-ui/react-icons";

function Detail() {
  const { id } = useParams();
  const [car, setCar] = useState<CarDetailInfo>();

  useEffect(() => {
    fetch(`http://localhost:8080/api/cars/${id}`)
      .then((res) => res.json())
      .then((data) => setCar(data));
  }, []);

  return !car ? (
    ""
  ) : (
    <Container className="p-4">
      <Button asChild variant="ghost">
        <Link to={"/"} relative="path" className="flex items-center gap-1">
          <ArrowLeftIcon />
          Back to cars
        </Link>
      </Button>

      <div className="grid grid-cols-1 gap-6 py-4 md:grid-cols-2 lg:grid-cols-3">
        <div className="col-span-2">
          <AspectRatio ratio={4 / 3}>
            <img
              src={carImg}
              alt="Car photo"
              style={{
                objectFit: "cover",
                width: "100%",
                height: "100%",
                borderRadius: "var(--radius-4)",
              }}
            />
          </AspectRatio>
        </div>
        <div className="col-span-2 lg:col-span-1">
          <p className="text-3xl text-center ">
            <span className="font-medium">{car.make}</span> {car.model}
          </p>
          <div className="flex flex-wrap gap-5 mt-3">
            <ParameterTile label="Category" value={car.category.name} />
            <ParameterTile
              label="Production year"
              value={car.productionYear!}
            />
            <ParameterTile label="Power" value={car.power!} unit="KM" />
            <ParameterTile label="Fuel" value={car.fuelType!.toLowerCase()} />
            <ParameterTile
              label="Transmission"
              value={car.transmissionType!.toLowerCase()}
            />
            <ParameterTile
              label="Drive type"
              value={car.driveType!.toLowerCase().replace("_", " ")}
            />
          </div>
          <Table.Root className="my-3">
            <Table.Header>
              <Table.Row>
                <Table.ColumnHeaderCell>
                  Reservation period
                </Table.ColumnHeaderCell>
                <Table.ColumnHeaderCell>
                  Price per day{" "}
                  <span className="font-normal opacity-70">[PLN]</span>
                </Table.ColumnHeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              <Table.Row>
                <Table.RowHeaderCell>One day</Table.RowHeaderCell>
                <Table.Cell>{car.price.forDay}</Table.Cell>
              </Table.Row>

              <Table.Row>
                <Table.RowHeaderCell>from 2 to 4 days</Table.RowHeaderCell>
                <Table.Cell>{car.price.forTwoToFourDays}</Table.Cell>
              </Table.Row>

              <Table.Row>
                <Table.RowHeaderCell>5 and more days</Table.RowHeaderCell>
                <Table.Cell>{car.price.forWeek}</Table.Cell>
              </Table.Row>
            </Table.Body>
          </Table.Root>

          <Button
            size={"3"}
            className="w-full"
            disabled={car.status === "UNAVAILABLE"}
            asChild={car.status !== "UNAVAILABLE"}
          >
            {car.status === "UNAVAILABLE" ? (
              "Reserve"
            ) : (
              <Link to={`/cars/${id}/reservation`}>Reserve</Link>
            )}
          </Button>
        </div>
      </div>
    </Container>
  );
}

export default Detail;
