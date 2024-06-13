import React, { useState, useEffect } from "react";
import "../index.css";
import AddCustomerForm from "./AddCustomerForm";
import EditCustomerForm from "./EditCustomerForm";

function CustomerList() {
  const [customers, setCustomers] = useState([]);
  const [showAddForm, setShowAddForm] = useState(false);
  const [editCustomer, setEditCustomer] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [searchField, setSearchField] = useState("firstName");

  useEffect(() => {
    fetchCustomers(1, 5, "firstName");
  }, []);

  const fetchCustomers = async (pageNo, rowsCount, sortBy) => {
    const apiUrl = `http://localhost:8080/customer/getCustomers?pageNo=${pageNo}&rowsCount=${rowsCount}&sortBy=${sortBy}&searchBy=`;
    const authToken = localStorage.getItem("jwtToken");
    const response = await fetch(apiUrl, {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });
    const data = await response.json();
    setCustomers(data.content);
  };
  const handleSync = async () => {
    let customersSync = [];
    const syncApiUrl = `http://localhost:8080/customer/sync`;
    const authToken = localStorage.getItem("jwtToken");

    try {
      const syncResponse = await fetch(syncApiUrl, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authToken}`,
        },
      });

      const syncData = await syncResponse.json();
      console.log("Sync response data:", syncData);

      customersSync = syncData.map((customer) => ({
        firstName: customer.first_name,
        lastName: customer.last_name,
        street: customer.street,
        address: customer.address,
        city: customer.city,
        state: customer.state,
        email: customer.email,
        phone: customer.phone,
      }));

      console.log(customersSync);

      for (const customer of customersSync) {
        const createApiUrl =
          "http://localhost:8080/customer/create?syncDb=true";
        await fetch(createApiUrl, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${authToken}`,
          },
          body: JSON.stringify(customer),
        });
      }

      fetchCustomers(1, 5, "firstName");
    } catch (error) {
      console.error("Error during sync operation:", error);
    }
  };
  const handleAddCustomer = async (newCustomer) => {
    setShowAddForm(false);
    fetchCustomers(1, 5, "firstName"); // Refresh the customer list
  };

  const handleEditCustomer = async (updatedCustomer) => {
    const authToken = localStorage.getItem("jwtToken");
    const response = await fetch(
      `http://localhost:8080/customer/update/${editCustomer.email}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authToken}`,
        },
        body: JSON.stringify(updatedCustomer),
      }
    );
    if (response.ok) {
      setEditCustomer(null);
      fetchCustomers(1, 5, "firstName"); // Refresh the customer list
    } else {
      console.error("Error updating customer");
    }
  };

  const deleteCustomer = async (email) => {
    const authToken = localStorage.getItem("jwtToken");
    const response = await fetch(
      `http://localhost:8080/customer/delete?email=${email}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${authToken}`,
        },
      }
    );
    if (response.ok) {
      fetchCustomers(1, 5, "firstName"); // Refresh the customer list
    } else {
      console.error("Error deleting customer");
    }
  };

  const handleSearch = async () => {
    const apiUrl = `http://localhost:8080/customer/searchBy?searchBy=${searchField}&searchQuery=${searchQuery}`;
    const authToken = localStorage.getItem("jwtToken");
    const response = await fetch(apiUrl, {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    });
    const data = await response.json();
    setCustomers(data);
  };

  return (
    <div className="table-container">
      <h1>Customer List</h1>
      <div id="btn">
        <button onClick={() => setShowAddForm(true)}>Add customer</button>
        <div>
          <label htmlFor="sortBy">Sort By</label>
          <select
            id="selectField"
            onChange={(e) => fetchCustomers(1, 5, e.target.value)}
          >
            <option value="firstName">First Name</option>
            <option value="city">City</option>
            <option value="email">Email</option>
            <option value="phone">Phone</option>
          </select>
        </div>
        <div style={{ display: "flex", alignItems:"center" }}>
          <label htmlFor="searchField">Search By</label>
          <select
            id="searchField"
            onChange={(e) => setSearchField(e.target.value)}
          >
            <option value="firstName">First Name</option>
            <option value="city">City</option>
            <option value="email">Email</option>
            <option value="phone">Phone</option>
          </select>
          <div>
            <input
              id="searchBar"
              type="text"
              placeholder="search"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <button className="btn" onClick={handleSearch}>
              Search
            </button>
          </div>
        </div>
        <button onClick={handleSync} id="sync-btn" className="btn">
          Sync
        </button>
      </div>
      <table>
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.email}>
              <td>{customer.firstName}</td>
              <td>{customer.lastName}</td>
              <td>{customer.address}</td>
              <td>{customer.city}</td>
              <td>{customer.state}</td>
              <td>{customer.email}</td>
              <td>{customer.phone}</td>
              <td>
                <div className="actions">
                  <button
                    className="btn"
                    onClick={() => setEditCustomer(customer)}
                  >
                    Edit
                  </button>
                  <button
                    className="btn"
                    onClick={() => deleteCustomer(customer.email)}
                  >
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pno">
        <button onClick={() => fetchCustomers(1, 5, "firstName")}>1</button>
        <button onClick={() => fetchCustomers(2, 5, "firstName")}>2</button>
        <button onClick={() => fetchCustomers(3, 5, "firstName")}>3</button>
      </div>
      {showAddForm && (
        <AddCustomerForm
          onClose={() => setShowAddForm(false)}
          onAddCustomer={handleAddCustomer}
        />
      )}
      {editCustomer && (
        <EditCustomerForm
          customer={editCustomer}
          onClose={() => setEditCustomer(null)}
          onEditCustomer={handleEditCustomer}
        />
      )}
    </div>
  );
}

export default CustomerList;
