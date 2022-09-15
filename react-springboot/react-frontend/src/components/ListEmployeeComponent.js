import React, {useEffect, useState} from 'react'
import {BrowserRouter, Link} from 'react-router-dom'
import EmployeeService from '../services/EmployeeService'

const ListEmployeeComponent = () => {

    const [employees, setEmployees] = useState([])

    useEffect(() => {
        getAllEmployees();
    }, [])

    const getAllEmployees = () => {
        EmployeeService.getEmployees().then((response) => {
            setEmployees(response.data)
            console.log(response.data)
        }).catch(error =>{
            console.log(error);
        })
    }

    const deleteEmployee = (employeeId) => {
        console.log(employeeId)

        EmployeeService.deleteEmployee(employeeId).then((response) => {
            console.log(response.data)
            getAllEmployees();
        }).catch(error =>{
            console.log(error);
        })
    }

    return (
        <div>
            <h2 className="text-center">Employees List</h2>
            <BrowserRouter forceRefresh={true}>
            <Link to = "/add-employee" className="btn btn-primary-mb-2">Add Employee</Link>
            </BrowserRouter>
                <div className="row">
                </div>
                <div className="row">
                    <table className = "table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th> Employee First Name </th>
                                <th> Employee Last Name </th>
                                <th> Employee Email </th>
                                <th> Actions </th>
                            </tr>
                        </thead>

                        <tbody>
                            {
                                employees.map(
                                    employee => 
                                    <tr key = {employee.id}>
                                        <td> {employee.firstName} </td>
                                        <td> {employee.lastName} </td>
                                        <td> {employee.emailId} </td>
                                        <td>
                                        <BrowserRouter forceRefresh={true}>
                                            <Link className='btn btn-info' to={`/update-employee/${employee.id}`}>Update</Link>
                                        </BrowserRouter>    
                                            <button className="btn btn-danger" onClick = {() => deleteEmployee(employee.id)} style={{marginLeft: "10px"}}>Delete</button>
                                        </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>


        </div>
    )

}

export default ListEmployeeComponent