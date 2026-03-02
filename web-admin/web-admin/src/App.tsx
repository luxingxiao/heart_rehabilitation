import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Layout, Menu, Typography } from 'antd';
import { UserOutlined, HomeOutlined } from '@ant-design/icons';
import './App.css';
import PatientList from './pages/PatientList';
import PatientDetail from './pages/PatientDetail';

const { Header, Content, Sider } = Layout;
const { Title } = Typography;

function App() {
  return (
    <Router>
      <Layout style={{ minHeight: '100vh' }}>
        <Header style={{ display: 'flex', alignItems: 'center', padding: '0 24px' }}>
          <Title level={3} style={{ color: 'white', margin: 0 }}>
            Heart Rehabilitation Admin
          </Title>
        </Header>
        <Layout>
          <Sider width={200} theme="light">
            <Menu mode="inline" defaultSelectedKeys={['patients']}>
              <Menu.Item key="home" icon={<HomeOutlined />}>
                <Link to="/">Dashboard</Link>
              </Menu.Item>
              <Menu.Item key="patients" icon={<UserOutlined />}>
                <Link to="/patients">Patient Management</Link>
              </Menu.Item>
            </Menu>
          </Sider>
          <Layout style={{ padding: '24px' }}>
            <Content style={{ background: 'white', padding: '24px', borderRadius: '8px' }}>
              <Routes>
                <Route path="/" element={
                  <div>
                    <Title level={2}>Welcome to Heart Rehabilitation Admin</Title>
                    <p>Select a menu item to begin.</p>
                  </div>
                } />
                <Route path="/patients" element={<PatientList />} />
                <Route path="/patients/:id" element={<PatientDetail />} />
                <Route path="/patients/new" element={<PatientDetail />} />
              </Routes>
            </Content>
          </Layout>
        </Layout>
      </Layout>
    </Router>
  );
}

export default App
