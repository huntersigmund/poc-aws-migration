import React, {useEffect, useState} from 'react'
import {BrowserRouter, Link, useHistory, useParams} from 'react-router-dom'
import EmployeeService from '../services/EmployeeService'

const AddEmployeeComponent = () => {

    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [emailId, setEmailId] = useState('')
    const history = useHistory();
    const {id} = useParams();

    const saveOrUpdateEmployee = (event) => {
        event.preventDefault();

        const employee = {firstName, lastName, emailId};
        if(id) {

            EmployeeService.updateEmployee(id, employee).then((response) => {
                console.log(response.data)
                history.push('/employees')
                history.go()
            }).catch(error => console.log(error))

        } else {

            console.log(employee)
            EmployeeService.createEmployee(employee).then((response) => {
                console.log(response.data)
                history.push('/employees')
                history.go()
            }).catch(error => console.log(error))

        }
    }

    useEffect(() => {

        EmployeeService.getEmployeeById(id).then((response) => {
            setFirstName(response.data.firstName)
            setLastName(response.data.lastName)
            setEmailId(response.data.emailId)
        }).catch(error => {
            console.log(error)
        })
    }, [])

    const title = () => {
        if(id) {
            console.log(id)
            return <h2 className="text-center">Update Employee</h2>
        } else {
            return <h2 className="text-center">Add Employee</h2>
        }

    }


    return (
        <div>
            <br></br> <br></br>
            <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3"> 
                        {
                            title()
                        }
                            <div className="card-body"></div>
                                <form>
                                    <div className="form-group">
                                        <label>First Name: </label>
                                        <input placeholder="First Name" name="firstName" className="form-control" value={firstName} onChange={(event) => {setFirstName(event.target.value)}}/>
                                    </div>
                                    <div className="form-group">
                                        <label>Last Name: </label>
                                        <input placeholder="Last Name" name="lastName" className="form-control" value={lastName} onChange={(event) => {setLastName(event.target.value)}}/>
                                    </div>
                                    <div className="form-group">
                                        <label>Email Address: </label>
                                        <input placeholder="Email" name="emailId" className="form-control" value={emailId} onChange={(event) => {setEmailId(event.target.value)}}/>
                                    </div>

                                        <button className="btn btn-success" onClick={(event) => saveOrUpdateEmployee(event)}>Save</button>
                                        <BrowserRouter forceRefresh={true}>
                                        <Link to="/employees" className="btn btn-danger" style={{marginLeft: "10px"}}>Cancel</Link>
                                        </BrowserRouter>
                                        <br></br><br></br>
                                </form>
                        </div>
                    </div>
                </div>
        </div>
    )
}

export default AddEmployeeComponent