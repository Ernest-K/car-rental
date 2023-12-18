import { EditCategoryInput } from "@/types/types";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
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
import useAuth from "@/hooks/useAuth";

function AddCategoryForm() {
  const navigate = useNavigate();
  const { getUser } = useAuth();

  const form = useForm<EditCategoryInput>({
    defaultValues: {
      name: "",
    },
  });

  const onSubmit: SubmitHandler<EditCategoryInput> = (data) => {
    fetch(`http://localhost:8080/api/categories`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Basic ${getUser()}`,
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        if (res.ok) {
          toast({
            title: "Category",
            description: "Category add has been processed successfully",
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
          Add
        </Button>
      </form>
    </Form>
  );
}

export default AddCategoryForm;
