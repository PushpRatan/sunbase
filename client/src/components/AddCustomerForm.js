import React, { useState } from "react";
import "../index.css";

function AddCustomerForm({ onClose, onAddCustomer }) {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    street: "",
    address: "",
    city: "",
    state: "",
    email: "",
    phone: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const authToken = localStorage.getItem("jwtToken");
    const apiUrl = "http://localhost:8080/customer/create?syncDb=false";
    await fetch(apiUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${authToken}`,
      },
      body: JSON.stringify(formData),
    });
    onAddCustomer();
  };

  return (
    <form
      id="addCustomer-form"
      className="addCustomer-form"
      onSubmit={handleSubmit}
    >
      <h1 id="form-heading">Add Customer</h1>
      <input
        type="text"
        name="firstName"
        placeholder="First Name"
        value={formData.firstName}
        onChange={handleChange}
        required
      />
      <input
        type="text"
        name="lastName"
        placeholder="Last Name"
        value={formData.lastName}
        onChange={handleChange}
        required
      />
      <input
        type="text"
        name="street"
        placeholder="Street"
        value={formData.street}
        onChange={handleChange}
        required
      />
      <input
        type="text"
        name="address"
        placeholder="Address"
        value={formData.address}
        onChange={handleChange}
        required
      />
      <input
        type="text"
        name="city"
        placeholder="City"
        value={formData.city}
        onChange={handleChange}
        required
      />
      <input
        type="text"
        name="state"
        placeholder="State"
        value={formData.state}
        onChange={handleChange}
        required
      />
      <input
        type="email"
        name="email"
        placeholder="Email"
        value={formData.email}
        onChange={handleChange}
        required
      />
      <input
        type="tel"
        name="phone"
        placeholder="Phone"
        value={formData.phone}
        onChange={handleChange}
        required
      />
      <div style={{ display: "flex", gap: "10px", justifyContent: "center" }}>
        <button type="submit">Add</button>
        <button type="button" onClick={onClose}>
          Cancel
        </button>
      </div>
    </form>
  );
}

export default AddCustomerForm;
