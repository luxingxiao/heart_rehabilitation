import React, { useState, useEffect } from 'react';
import {
  Table,
  Input,
  Button,
  Space,
  Select,
  Tag,
  Card,
  Row,
  Col,
  Typography,
  message,
  Popconfirm,
} from 'antd';
import { SearchOutlined, PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { Patient, PatientStatus } from '../../services/patientService';
import patientService from '../../services/patientService';

const { Search } = Input;
const { Option } = Select;
const { Title } = Typography;

const PatientList: React.FC = () => {
  const [patients, setPatients] = useState<Patient[]>([]);
  const [loading, setLoading] = useState(false);
  const [searchText, setSearchText] = useState('');
  const [statusFilter, setStatusFilter] = useState<string | undefined>(undefined);

  useEffect(() => {
    loadPatients();
  }, []);

  const loadPatients = async () => {
    setLoading(true);
    try {
      const data = await patientService.getPatients();
      setPatients(data);
    } catch (error) {
      message.error('Failed to load patients');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (value: string) => {
    setSearchText(value);
  };

  const handleStatusFilter = (value: string) => {
    setStatusFilter(value);
  };

  const handleDelete = async (id: string) => {
    try {
      await patientService.deletePatient(id);
      message.success('Patient deleted successfully');
      loadPatients();
    } catch (error) {
      message.error('Failed to delete patient');
    }
  };

  const filteredPatients = patients.filter(patient => {
    const matchesSearch = searchText === '' ||
      patient.name.toLowerCase().includes(searchText.toLowerCase()) ||
      patient.id.toLowerCase().includes(searchText.toLowerCase()) ||
      patient.phone?.includes(searchText);
    const matchesStatus = !statusFilter || patient.status === statusFilter;
    return matchesSearch && matchesStatus;
  });

  const columns = [
    {
      title: 'Patient ID',
      dataIndex: 'id',
      key: 'id',
      render: (id: string) => <Link to={`/patients/${id}`}>{id}</Link>,
    },
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      render: (name: string, record: Patient) => (
        <Link to={`/patients/${record.id}`}>{name}</Link>
      ),
    },
    {
      title: 'Gender',
      dataIndex: 'gender',
      key: 'gender',
    },
    {
      title: 'Age',
      dataIndex: 'age',
      key: 'age',
    },
    {
      title: 'Phone',
      dataIndex: 'phone',
      key: 'phone',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status: PatientStatus) => {
        const color = status === 'active' ? 'green' : status === 'inactive' ? 'red' : 'default';
        return <Tag color={color}>{status.toUpperCase()}</Tag>;
      },
    },
    {
      title: 'Last Visit',
      dataIndex: 'lastVisit',
      key: 'lastVisit',
      render: (date: string) => new Date(date).toLocaleDateString(),
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_: any, record: Patient) => (
        <Space size="small">
          <Button
            type="link"
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/patients/${record.id}/edit`}
          >
            Edit
          </Button>
          <Popconfirm
            title="Are you sure to delete this patient?"
            onConfirm={() => handleDelete(record.id)}
            okText="Yes"
            cancelText="No"
          >
            <Button type="link" danger icon={<DeleteOutlined />}>
              Delete
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <Card>
      <Row justify="space-between" align="middle" style={{ marginBottom: 16 }}>
        <Col>
          <Title level={3}>Patient Management</Title>
        </Col>
        <Col>
          <Button type="primary" icon={<PlusOutlined />}>
            <Link to="/patients/new">Add New Patient</Link>
          </Button>
        </Col>
      </Row>
      <Row gutter={16} style={{ marginBottom: 16 }}>
        <Col span={12}>
          <Search
            placeholder="Search by ID, name, or phone"
            allowClear
            enterButton={<SearchOutlined />}
            size="large"
            onSearch={handleSearch}
            onChange={e => handleSearch(e.target.value)}
          />
        </Col>
        <Col span={6}>
          <Select
            placeholder="Filter by status"
            allowClear
            style={{ width: '100%' }}
            size="large"
            onChange={handleStatusFilter}
          >
            <Option value="active">Active</Option>
            <Option value="inactive">Inactive</Option>
            <Option value="pending">Pending</Option>
          </Select>
        </Col>
        <Col span={6}>
          <Button size="large" onClick={loadPatients}>
            Refresh
          </Button>
        </Col>
      </Row>
      <Table
        columns={columns}
        dataSource={filteredPatients}
        rowKey="id"
        loading={loading}
        pagination={{ pageSize: 10 }}
      />
    </Card>
  );
};

export default PatientList;