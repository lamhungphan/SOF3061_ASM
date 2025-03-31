import { createClient } from "@supabase/supabase-js";

// Thay thế bằng thông tin từ Supabase Console (Settings > API)
const supabaseUrl = "https://wdkyyamhfjpwjcysnemg.supabase.co";
const supabaseAnonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Indka3l5YW1oZmpwd2pjeXNuZW1nIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDI5NzQzOTcsImV4cCI6MjA1ODU1MDM5N30.NioQur7KKIkgmUOqIyeJJ8oZayvO--186B-vstiqPD0";

export const supabase = createClient(supabaseUrl, supabaseAnonKey);
