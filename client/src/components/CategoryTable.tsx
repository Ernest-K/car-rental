import { Category } from "@/types/interfaces";
import { Pencil2Icon, TrashIcon } from "@radix-ui/react-icons";
import { Button, Table } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "./ui/use-toast";
import useAuth from "@/hooks/useAuth";

function CategoryTable() {
  const [categories, setCategories] = useState<Category[]>([]);
  const { getUser } = useAuth();

  useEffect(() => {
    fetch("http://localhost:8080/api/categories")
      .then((res) => res.json())
      .then((data) => setCategories(data));
  }, []);

  const deleteCategory = (id: number) => {
    fetch(`http://localhost:8080/api/categories/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Basic ${getUser()}`,
      },
    })
      .then((res) => {
        if (res.ok) {
          return res.text();
        }
      })
      .then((data) => {
        setCategories(categories.filter((category) => category.id != id));
        toast({
          title: "Category",
          description: data,
        });
      });
  };

  return !categories.length ? (
    <p className="text-center p-3">No categories</p>
  ) : (
    <Table.Root className="m-3" variant="surface">
      <Table.Header>
        <Table.Row>
          <Table.ColumnHeaderCell>Id</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Name</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell className="text-center">
            Operation
          </Table.ColumnHeaderCell>
        </Table.Row>
      </Table.Header>

      <Table.Body>
        {categories.map((category) => (
          <Table.Row key={category.id}>
            <Table.RowHeaderCell>{category.id}</Table.RowHeaderCell>
            <Table.Cell>{category.name}</Table.Cell>
            <Table.Cell>
              <div className="w-full flex justify-center gap-10">
                <Button size={"3"} variant="ghost">
                  <Link to={`/dashboard/category/${category.id}/edit`}>
                    <Pencil2Icon width="16" height="16" />
                  </Link>
                </Button>
                <Button
                  size={"3"}
                  variant="ghost"
                  color="red"
                  onClick={() => deleteCategory(category.id)}
                >
                  <TrashIcon
                    width="16"
                    height="16"
                    className="cursor-pointer"
                  />
                </Button>
              </div>
            </Table.Cell>
          </Table.Row>
        ))}
      </Table.Body>
    </Table.Root>
  );
}

export default CategoryTable;
