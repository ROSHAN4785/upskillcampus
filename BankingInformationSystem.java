
import tkinter as tk
from tkinter import messagebox
import pyshorteners
import qrcode

class URLShortenerApp:
    def __init__(self, master):
        self.master = master
        master.title("URL Shortener")
        master.geometry("500x400")
        master.configure(bg="#f0f0f0")

        self.header_label = tk.Label(master, text="URL Shortener", font=("Helvetica", 24, "bold"), bg="#4285F4", fg="white")
        self.header_label.pack(fill=tk.X, pady=10)

        self.label = tk.Label(master, text="Enter URL:", font=("Helvetica", 14), bg="#f0f0f0")
        self.label.pack()

        self.entry = tk.Entry(master, width=40, font=("Helvetica", 12))
        self.entry.pack()

        self.shorten_button = tk.Button(master, text="Shorten URL", command=self.shorten_link, bg="#0F9D58", fg="white", font=("Helvetica", 12), relief=tk.RAISED)
        self.shorten_button.pack(pady=10)

        self.shortened_label = tk.Label(master, text="", font=("Helvetica", 12), wraplength=400, bg="#f0f0f0")
        self.shortened_label.pack()

        self.copy_button = tk.Button(master, text="Copy", command=self.copy_to_clipboard, state=tk.DISABLED, font=("Helvetica", 12), relief=tk.RAISED)
        self.copy_button.pack(pady=10)

        self.qr_button = tk.Button(master, text="Generate QR Code", command=self.generate_qr, bg="#DB4437", fg="white", font=("Helvetica", 12), relief=tk.RAISED)
        self.qr_button.pack(pady=10)

        self.history_label = tk.Label(master, text="Shortened URL History:", font=("Helvetica", 14), bg="#f0f0f0")
        self.history_label.pack()

        self.history_listbox = tk.Listbox(master, selectmode=tk.SINGLE, font=("Helvetica", 12), height=5, width=50)
        self.history_listbox.pack(pady=10)

        self.clear_button = tk.Button(master, text="Clear History", command=self.clear_history, bg="#4285F4", fg="white", font=("Helvetica", 12), relief=tk.RAISED)
        self.clear_button.pack(pady=10)

        self.animate_widgets()

    def animate_widgets(self):
        # Add animations or interactive features here
        pass

    def shorten_link(self):
        original_url = self.entry.get()
        if not original_url:
            messagebox.showerror("Error", "Please enter a URL to shorten.")
            return

        try:
            s = pyshorteners.Shortener()
            shortened_url = s.tinyurl.short(original_url)
            self.shortened_label.config(text=f"Shortened URL:\n{shortened_url}")
            self.copy_button.config(state=tk.NORMAL)
            self.history_listbox.insert(tk.END, shortened_url)
        except Exception as e:
            messagebox.showerror("Error", f"An error occurred: {e}")

    def copy_to_clipboard(self):
        shortened_url = self.shortened_label.cget("text").split(": ")[1]
        self.master.clipboard_clear()
        self.master.clipboard_append(shortened_url)
        messagebox.showinfo("Success", "Shortened URL copied to clipboard!")

    def generate_qr(self):
        original_url = self.entry.get()
        if original_url:
            qr_img = self.generate_qr_code(original_url)
            qr_img.show()  # Show QR code using default image viewer

    def generate_qr_code(self, url):
        qr = qrcode.QRCode(version=1, error_correction=qrcode.constants.ERROR_CORRECT_L, box_size=10, border=4)
        qr.add_data(url)
        qr.make(fit=True)
        img = qr.make_image(fill_color="black", back_color="white")
        return img

    def clear_history(self):
        self.history_listbox.delete(0, tk.END)

def main():
    root = tk.Tk()
    app = URLShortenerApp(root)
    root.mainloop()

if __name__ == "__main__":
    main()




