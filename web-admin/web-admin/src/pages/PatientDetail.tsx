import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import {
  Form,
  Input,
  InputNumber,
  Select,
  Button,
  Card,
  Row,
  Col,
  Typography,
  message,
  Space,
  Tag,
  Divider,
} from 'antd';
import { ArrowLeftOutlined, SaveOutlined, DeleteOutlined } from '@ant-design/icons';
import type { Patient, PatientStatus } from '../services/patientService';
import patientService from '../services/patientService';

type PatientFormValues = Omit<Patient, 'id' | 'createdAt' | 'updatedAt' | 'lastVisit'>;

const { Option } = Select;
const { Title, Text } = Typography;
const { TextArea } = Input;

const PatientDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [patient, setPatient] = useState<Patient | null>(null);
  const [isEditing, setIsEditing] = useState(!id || id === 'new');

  const loadPatient = useCallback(async (patientId: string) => {
    setLoading(true);
    try {
      const data = await patientService.getPatient(patientId);
      if (data) {
        setPatient(data);
        form.setFieldsValue(data);
        setIsEditing(false);
      } else {
        message.error('Patient not found');
        navigate('/patients');
      }
    } catch {
      message.error('Failed to load patient details');
    } finally {
      setLoading(false);
    }
  }, [form, navigate]);

  useEffect(() => {
    if (id && id !== 'new') {
      loadPatient(id);
    } else {
      form.resetFields();
    }
  }, [id, loadPatient, form]);


  const handleSave = async (values: PatientFormValues) => {
    setLoading(true);
    try {
      if (id === 'new') {
        // Create new patient
        const newPatient = await patientService.createPatient({
          name: values.name,
          gender: values.gender,
          age: values.age,
          phone: values.phone,
          email: values.email,
          address: values.address,
          status: values.status,
        });
        message.success('Patient created successfully');
        navigate(`/patients/${newPatient.id}`);
      } else if (patient) {
        // Update existing patient
        const updated = await patientService.updatePatient(patient.id, values);
        if (updated) {
          setPatient(updated);
          message.success('Patient updated successfully');
          setIsEditing(false);
        } else {
          message.error('Failed to update patient');
        }
      }
    } catch {
      message.error('Failed to save patient');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!patient) return;
    try {
      const success = await patientService.deletePatient(patient.id);
      if (success) {
        message.success('Patient deleted successfully');
        navigate('/patients');
      } else {
        message.error('Failed to delete patient');
      }
    } catch {
      message.error('Failed to delete patient');
    }
  };

  const statusColors: Record<PatientStatus, string> = {
    active: 'green',
    inactive: 'red',
    pending: 'orange',
  };

  return (
    <Card>
      <Space direction="vertical" style={{ width: '100%' }} size="large">
        <Row justify="space-between" align="middle">
          <Col>
            <Space>
              <Link to="/patients">
                <Button icon={<ArrowLeftOutlined />}>Back to List</Button>
              </Link>
              <Title level={3} style={{ margin: 0 }}>
                {id === 'new' ? 'Create New Patient' : `Patient ${patient?.id || ''}`}
              </Title>
            </Space>
          </Col>
          <Col>
            {patient && !isEditing && (
              <Space>
                <Button type="primary" onClick={() => setIsEditing(true)}>
                  Edit
                </Button>
                <Button danger icon={<DeleteOutlined />} onClick={handleDelete}>
                  Delete
                </Button>
              </Space>
            )}
          </Col>
        </Row>

        <Divider />

        {patient && !isEditing ? (
          <Row gutter={24}>
            <Col span={8}>
              <Text strong>Patient ID</Text>
              <p>{patient.id}</p>
            </Col>
            <Col span={8}>
              <Text strong>Name</Text>
              <p>{patient.name}</p>
            </Col>
            <Col span={8}>
              <Text strong>Gender</Text>
              <p>{patient.gender}</p>
            </Col>
            <Col span={8}>
              <Text strong>Age</Text>
              <p>{patient.age}</p>
            </Col>
            <Col span={8}>
              <Text strong>Phone</Text>
              <p>{patient.phone || 'N/A'}</p>
            </Col>
            <Col span={8}>
              <Text strong>Email</Text>
              <p>{patient.email || 'N/A'}</p>
            </Col>
            <Col span={8}>
              <Text strong>Status</Text>
              <p>
                <Tag color={statusColors[patient.status]}>{patient.status.toUpperCase()}</Tag>
              </p>
            </Col>
            <Col span={8}>
              <Text strong>Last Visit</Text>
              <p>{new Date(patient.lastVisit).toLocaleDateString()}</p>
            </Col>
            <Col span={8}>
              <Text strong>Address</Text>
              <p>{patient.address || 'N/A'}</p>
            </Col>
            <Col span={24}>
              <Text strong>Created</Text>
              <p>{new Date(patient.createdAt).toLocaleString()}</p>
            </Col>
            <Col span={24}>
              <Text strong>Last Updated</Text>
              <p>{new Date(patient.updatedAt).toLocaleString()}</p>
            </Col>
          </Row>
        ) : (
          <Form
            form={form}
            layout="vertical"
            onFinish={handleSave}
            initialValues={{
              status: 'active',
              gender: 'male',
            }}
          >
            <Row gutter={24}>
              <Col span={12}>
                <Form.Item
                  label="Full Name"
                  name="name"
                  rules={[{ required: true, message: 'Please enter patient name' }]}
                >
                  <Input placeholder="Enter patient name" />
                </Form.Item>
              </Col>
              <Col span={6}>
                <Form.Item
                  label="Gender"
                  name="gender"
                  rules={[{ required: true, message: 'Please select gender' }]}
                >
                  <Select>
                    <Option value="male">Male</Option>
                    <Option value="female">Female</Option>
                    <Option value="other">Other</Option>
                  </Select>
                </Form.Item>
              </Col>
              <Col span={6}>
                <Form.Item
                  label="Age"
                  name="age"
                  rules={[{ required: true, message: 'Please enter age' }]}
                >
                  <InputNumber min={0} max={150} style={{ width: '100%' }} />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item label="Phone" name="phone">
                  <Input placeholder="Enter phone number" />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item label="Email" name="email">
                  <Input placeholder="Enter email address" />
                </Form.Item>
              </Col>
              <Col span={12}>
                <Form.Item label="Status" name="status">
                  <Select>
                    <Option value="active">Active</Option>
                    <Option value="inactive">Inactive</Option>
                    <Option value="pending">Pending</Option>
                  </Select>
                </Form.Item>
              </Col>
              <Col span={24}>
                <Form.Item label="Address" name="address">
                  <TextArea rows={3} placeholder="Enter full address" />
                </Form.Item>
              </Col>
            </Row>
            <Row justify="end">
              <Space>
                <Button onClick={() => {
                  if (patient) {
                    setIsEditing(false);
                    form.setFieldsValue(patient);
                  } else {
                    navigate('/patients');
                  }
                }}>
                  Cancel
                </Button>
                <Button type="primary" htmlType="submit" loading={loading} icon={<SaveOutlined />}>
                  Save
                </Button>
              </Space>
            </Row>
          </Form>
        )}
      </Space>
    </Card>
  );
};

export default PatientDetail;