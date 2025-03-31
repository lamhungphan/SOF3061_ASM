import { supabase } from "@/supabaseClient";

export const uploadImage = async (file) => {
  if (!file) return null;

  const fileName = `${Date.now()}-${file.name}`; // Tránh trùng tên file
  const { data, error } = await supabase.storage
    .from("uploads") // Tên bucket
    .upload(fileName, file);

  if (error) {
    console.error("Lỗi khi upload ảnh:", error);
    return null;
  }

  // Lấy URL của ảnh
  const { data: urlData } = supabase.storage.from("uploads").getPublicUrl(fileName);
  return urlData.publicUrl;
};
