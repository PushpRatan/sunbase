import React, { useState, useEffect } from "react";
import "../index.css";

function EditCustomerForm({ customer, onClose, onEditCustomer }) {
  const [firstName, setFirstName] = useState(customer.firstName);
  const [lastName, setLastName] = useState(customer.lastName);
  const [address, setAddress] = useState(customer.address);
  const [city, setCity] = useState(customer.city);
  const [state, setState] = useState(customer.state);
  const [email, setEmail] = useState(customer.email);
  const [phone, setPhone] = useState(customer.phone);

  useEffect(() => {
    setFirstName(customer.firstName);
    setLastName(customer.lastName);
    setAddress(customer.address);
    setCity(customer.city);
    setState(customer.state);
    setEmail(customer.email);
    setPhone(customer.phone);
  }, [customer]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const updatedCustomer = {
      firstName,
      lastName,
      address,
      city,
      state,
      email,
      phone,
    };
    onEditCustomer(updatedCustomer);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h1>Edit Customer</h1>
      <input
        type="text"
        placeholder="First Name"
        value={firstName}
        onChange={(e) => setFirstName(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Last Name"
        value={lastName}
        onChange={(e) => setLastName(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Address"
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="City"
        value={city}
        onChange={(e) => setCity(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="State"
        value={state}
        onChange={(e) => setState(e.target.value)}
        required
      />
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Phone"
        value={phone}
        onChange={(e) => setPhone(e.target.value)}
        required
      />
      <div style={{ display: "flex", gap: "10px", justifyContent:"center" }}>
        <button type="submit">Submit</button>
        <button type="button" onClick={onClose}>
          Cancel
        </button>
      </div>
    </form>
  );
}

export default EditCustomerForm;
