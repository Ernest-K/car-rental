import { Category } from "@/types/interfaces";
import { EditCategoryInput } from "@/types/types";
import { useEffect, useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "./ui/form";
import { Input } from "./ui/input";
import { Button } from "@radix-ui/themes";
import { toast } from "./ui/use-toast";

function EditCarForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [category, setCategory] = useState<Category>();

  useEffect(() => {
    fetch(`http://localhost:8080/api/categories`)
      .then((res) => {
        if (res.ok) {
          return res.json();
        }
      })
      .then((data: Category[]) => {
        const cat = data.filter((cat) => cat.id === Number(id))[0];
        setCategory(cat);
        setValues(cat);
      });
  }, [id]);

  const setValues = (category: Category) => {
    form.setValue("name", category.name);
  };

  const form = useForm<EditCategoryInput>({
    defaultValues: {
      name: "",
    },
  });

  const onSubmit: SubmitHandler<EditCategoryInput> = (data) => {
    fetch(`http://localhost:8080/api/categories/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        if (res.ok) {
          toast({
            title: "Category",
            description: "Category update has been processed successfully",
          });
          navigate("/dashboard");
        } else {
          return res.json();
        }
      })
      .then((data) => {
        toast({
          variant: "destructive",
          title: "Category",
          description: data.errors.join(", "),
        });
      });
  };

  return (
    category && (
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="md:col-start-2 flex flex-col gap-2"
        >
          <FormField
            control={form.control}
            name="name"
            rules={{
              required: {
                value: true,
                message: "Please enter category name",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Category name</FormLabel>
                <FormControl>
                  <Input placeholder="Category name" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <Button className="w-full" type="submit">
            Save
          </Button>
        </form>
      </Form>
    )
  );
}

export default EditCarForm;
